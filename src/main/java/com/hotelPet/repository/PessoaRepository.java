package com.hotelPet.repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.hotelPet.codec.CodecPessoas;
import com.hotelPet.model.Pessoa;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

@Repository
public class PessoaRepository {

    public MongoDatabase conecta(){
                //Instânciado o CODEC
        Codec<Document> codec = MongoClient.getDefaultCodecRegistry()
                .get(Document.class);
        
        //Passar para a classe de codificação qual será o codec usado
        CodecPessoas pessoaCodec = new CodecPessoas(codec);
        
        //Regristo do codec usado no MongoClient
        CodecRegistry registro = CodecRegistries.fromRegistries(
                MongoClient.getDefaultCodecRegistry(), 
                CodecRegistries.fromCodecs(pessoaCodec)
        );
        
        //Fazer o build
        MongoClientOptions op = MongoClientOptions.builder()
                .codecRegistry(registro).build();
        
        MongoClient cliente = new MongoClient("localhost:27017", op);
        MongoDatabase db = cliente.getDatabase("Hotel");
        return db;
    }
    
    public void salvar(Pessoa pessoa) {
        MongoDatabase db = conecta();
        MongoCollection<Pessoa> pessoas = db.getCollection("pessoas", Pessoa.class);
        //Se eu já tiver um pessoa simplesmente atualizo ele
        if(pessoa.getId() == null){
            pessoas.insertOne(pessoa);
        }else{
            pessoas.updateOne(Filters.eq("_id",pessoa.getId()), new Document("$set",pessoa));
        }
        
        //cliente.close();
    }
    
    public List<Pessoa> listar (){
        MongoDatabase db = conecta();
        MongoCollection<Pessoa> pessoas = db.getCollection("pessoas", Pessoa.class);
        MongoCursor<Pessoa> resultado = pessoas.find().iterator();
        
        //Lista de Iteração
        List<Pessoa> pessoaLista = new ArrayList<>();
        
        while(resultado.hasNext()){
            Pessoa pessoa = resultado.next();
            pessoaLista.add(pessoa);
        }
        
        return pessoaLista;
    }
    
    public Pessoa obterId(String id){
        MongoDatabase db = conecta();
        MongoCollection<Pessoa> pessoas = db.getCollection("pessoas", Pessoa.class);
        Pessoa pessoa = pessoas.find(Filters.eq("_id", new ObjectId(id))).first();
        return pessoa;
    }
    
     public void excluir(String id) {
        MongoDatabase db = conecta();
        MongoCollection<Pessoa> tutores = db.getCollection("pessoas", Pessoa.class);
        tutores.findOneAndDelete(Filters.eq("_id", new ObjectId(id)));
    }
}


