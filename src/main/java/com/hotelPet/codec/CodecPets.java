package com.hotelPet.codec;

import com.hotelPet.model.Pessoa;
import com.hotelPet.model.Pet;
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

public class CodecPets implements CollectibleCodec<Pet>{
    //Atributo para criação de documento
    private Codec<Document> codec;
    
    //Construtor
    public CodecPets(Codec<Document> codec) {
        this.codec = codec;
    }

    @Override
    public Pet generateIdIfAbsentFromDocument(Pet pet) {
        return documentHasId(pet) ? pet.criarId() : pet;
    }

    @Override
    public boolean documentHasId(Pet pet) {
        //esse método só verifica se o objeto chamado tem ID
        return pet.getId() == null;
    }

    @Override
    public BsonValue getDocumentId(Pet pet) {
        //Verifica se o ID foi criado
        if(!documentHasId(pet)){
            throw new IllegalStateException("Esse documento não tem um Id");
        }else{
            //Para que o ID possa ser lido é preciso transformar ele
            //em uma base hexadecimal
            return new BsonString(pet.getId().toHexString());
        }
    }

    @Override
    public void encode(BsonWriter writer, Pet pet, EncoderContext ec) {

        ObjectId id = pet.getId();
        String nome = pet.getNome();
        String especie = pet.getEspecie();
        String tipo = pet.getTipo();
        String hospedagem = pet.getHospedagemAtual();
        Pessoa tutor = pet.getTutor();

        
        Document doc = new Document();
        doc.put("_id", id);
        doc.put("nome",nome);
        doc.put("especie",especie);
        doc.put("tipo", tipo);
        doc.put("hospedagemAtual", hospedagem);
        doc.put("tutor", new Document("id", tutor.getId())
                .append("nome", tutor.getNome())
                .append("telefone", tutor.getTelefone()));
        codec.encode(writer, doc, ec);
    }

    @Override
    public Class<Pet> getEncoderClass() {
        //É preciso informar a classe a ser interpretada
        return Pet.class;
    }

    @Override
    public Pet decode(BsonReader reader, DecoderContext dc) {
        /*Aqui fizemos o processo inverso do decode, vamos dizer para o SPRING
        como o objeto será retornado*/
        Document doc = codec.decode(reader, dc);
        Pet pet = new Pet();
        pet.setId(doc.getObjectId("_id"));
        pet.setNome(doc.getString("nome"));
        pet.setEspecie(doc.getString("especie"));
        pet.setTipo(doc.getString("tipo"));
        pet.setHospedagemAtual(doc.getString("hospedagemAtual"));
        
        Document tutorDoc = doc.get("tutor", Document.class);
        
        Pessoa tutor = new Pessoa();
        tutor.setId(tutorDoc.getObjectId("id"));
        tutor.setNome(tutorDoc.getString("nome")); 
        tutor.setTelefone(tutorDoc.getString("telefone"));
        pet.setTutor(tutor);

        return pet;
    }    
}
