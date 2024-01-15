package com.hotelPet.repository;

import com.hotelPet.codec.CodecChecks;
import com.hotelPet.model.CheckInOut;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

@Repository
public class CheckInOutRepository {

    public MongoDatabase conecta(){
                //Instânciado o CODEC
        Codec<Document> codec = MongoClient.getDefaultCodecRegistry()
                .get(Document.class);
        
        //Passar para a classe de codificação qual será o codec usado
        Codec checkCodec = new CodecChecks(codec);
        
        //Regristo do codec usado no MongoClient
        CodecRegistry registro = CodecRegistries.fromRegistries(
                MongoClient.getDefaultCodecRegistry(), 
                CodecRegistries.fromCodecs(checkCodec)
        );
        
        //Fazer o build
        MongoClientOptions op = MongoClientOptions.builder()
                .codecRegistry(registro).build();
        
        MongoClient cliente = new MongoClient("localhost:27017", op);
        MongoDatabase db = cliente.getDatabase("Hotel");
        return db;
    }
    
    public void salvar(CheckInOut check) {
        MongoDatabase db = conecta();
        MongoCollection<CheckInOut> checks = db.getCollection("checkInOuts", CheckInOut.class);
        //Se eu já tiver um pessoa simplesmente atualizo ele
        if(check.getId() == null){
            checks.insertOne(check);
        }else{
            checks.updateOne(Filters.eq("_id",check.getId()), new Document("$set",check));
        }
        
        //cliente.close();
    }
    
    public List<CheckInOut> listar (){
        MongoDatabase db = conecta();
        MongoCollection<CheckInOut> checks = db.getCollection("checkInOuts", CheckInOut.class);
        MongoCursor<CheckInOut> resultado = checks.find().iterator();
        
        //Lista de Iteração
        List<CheckInOut> checkLista = new ArrayList<>();
        
        while(resultado.hasNext()){
            CheckInOut check = resultado.next();
            checkLista.add(check);
        }
        
        return checkLista;
    }
    
    public CheckInOut obterId(String id){
        MongoDatabase db = conecta();
        MongoCollection<CheckInOut> checks = db.getCollection("checks", CheckInOut.class);
        CheckInOut check = checks.find(Filters.eq("_id", new ObjectId(id))).first();
        return check;
    }
    
     public void excluir(String id) {
        MongoDatabase db = conecta();
        MongoCollection<CheckInOut> checks = db.getCollection("checkInOuts", CheckInOut.class);
        checks.findOneAndDelete(Filters.eq("_id", new ObjectId(id)));
    }
}


