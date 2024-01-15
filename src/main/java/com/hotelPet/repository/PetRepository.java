package com.hotelPet.repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.hotelPet.codec.CodecPets;
import com.hotelPet.model.Pessoa;
import com.hotelPet.model.Pet;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

@Repository
public class PetRepository {

    public MongoDatabase conecta(){
                //Instânciado o CODEC
        Codec<Document> codec = MongoClient.getDefaultCodecRegistry()
                .get(Document.class);
        
        //Passar para a classe de codificação qual será o codec usado
        CodecPets codecPet = new CodecPets(codec);
        
        //Regristo do codec usado no MongoClient
        CodecRegistry registro = CodecRegistries.fromRegistries(
                MongoClient.getDefaultCodecRegistry(), 
                CodecRegistries.fromCodecs(codecPet)
        );
        
        //Fazer o build
        MongoClientOptions op = MongoClientOptions.builder()
                .codecRegistry(registro).build();
        
        MongoClient cliente = new MongoClient("localhost:27017", op);
        MongoDatabase db = cliente.getDatabase("Hotel");
        return db;
    }
    
    public void salvar(Pet pet) {
        PessoaRepository repo = new PessoaRepository();
        
        MongoDatabase db = conecta();
        MongoCollection<Pet> pets = db.getCollection("pets", Pet.class);
        
       Pessoa tutor = repo.obterId(pet.getTutor().getId().toString());
       pet.setTutor(tutor);
        
        
        //Se eu já tiver um pet simplesmente atualizo ele
        if(pet.getId() == null){
            pets.insertOne(pet);
        }else{
            pets.updateOne(Filters.eq("_id",pet.getId()), new Document("$set",pet));
        }
        
        //cliente.close();
    }
    
    public List<Pet> listar (){
        MongoDatabase db = conecta();
        MongoCollection<Pet> pets = db.getCollection("pets", Pet.class);
        MongoCursor<Pet> resultado = pets.find().iterator();
        
        //Lista de Iteração
        List<Pet> petLista = new ArrayList<>();
        
        while(resultado.hasNext()){
            Pet pet = resultado.next();
            petLista.add(pet);
        }
        
        return petLista;
    }
    
    public Pet obterId(String id){
        MongoDatabase db = conecta();
        MongoCollection<Pet> pets = db.getCollection("pets", Pet.class);
        Pet pet = pets.find(Filters.eq("_id", new ObjectId(id))).first();
        return pet;
    }
    
    public void excluir(String id) {
        MongoDatabase db = conecta();
        MongoCollection<Pet> tutores = db.getCollection("pets", Pet.class);
        tutores.findOneAndDelete(Filters.eq("_id", new ObjectId(id)));
    }
}


