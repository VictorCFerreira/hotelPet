package com.hotelPet.codec;

import com.hotelPet.model.Pessoa;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

public class CodecPessoas implements CollectibleCodec<Pessoa>{
    //Atributo para criação de documento
    private Codec<Document> codec;
    
    //Construtor
    public CodecPessoas(Codec<Document> codec) {
        this.codec = codec;
    }

    @Override
    public Pessoa generateIdIfAbsentFromDocument(Pessoa aluno) {
        return documentHasId(aluno) ? aluno.criarId() : aluno;
    }

    @Override
    public boolean documentHasId(Pessoa aluno) {
        //esse método só verifica se o objeto chamado tem ID
        return aluno.getId() == null;
    }

    @Override
    public BsonValue getDocumentId(Pessoa aluno) {
        //Verifica se o ID foi criado
        if(!documentHasId(aluno)){
            throw new IllegalStateException("Esse documento não tem um Id");
        }else{
            //Para que o ID possa ser lido é preciso transformar ele
            //em uma base hexadecimal
            return new BsonString(aluno.getId().toHexString());
        }
    }

    @Override
    public void encode(BsonWriter writer, Pessoa pessoa, EncoderContext ec) {
        ObjectId id = pessoa.getId();
        String nome = pessoa.getNome();
        String telefone = pessoa.getTelefone();
        
        Document doc = new Document();
        doc.put("_id", id);
        doc.put("nome",nome);
        doc.put("telefone", telefone);
        codec.encode(writer, doc, ec);
    }

    @Override
    public Class<Pessoa> getEncoderClass() {
        return Pessoa.class;
    }

    @Override
    public Pessoa decode(BsonReader reader, DecoderContext dc) {
        Document doc = codec.decode(reader, dc);
        Pessoa pessoa = new Pessoa();
        pessoa.setId(doc.getObjectId("_id"));
        pessoa.setNome(doc.getString("nome"));
        pessoa.setTelefone(doc.getString("telefone"));
 
        return pessoa;
    }    
}
