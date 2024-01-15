package com.hotelPet.codec;

import com.hotelPet.model.CheckInOut;
import java.util.Date;
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

public class CodecChecks implements CollectibleCodec<CheckInOut>{
    //Atributo para criação de documento
    private Codec<Document> codec;
    
    //Construtor
    public CodecChecks(Codec<Document> codec) {
        this.codec = codec;
    }

    @Override
    public CheckInOut generateIdIfAbsentFromDocument(CheckInOut check) {
        return documentHasId(check) ? check.criarId() : check;
    }

    @Override
    public boolean documentHasId(CheckInOut check) {
        //esse método só verifica se o objeto chamado tem ID
        return check.getId() == null;
    }

    @Override
    public BsonValue getDocumentId(CheckInOut check) {
        //Verifica se o ID foi criado
        if(!documentHasId(check)){
            throw new IllegalStateException("Esse documento não tem um Id");
        }else{
            //Para que o ID possa ser lido é preciso transformar ele
            //em uma base hexadecimal
            return new BsonString(check.getId().toHexString());
        }
    }

    @Override
    public void encode(BsonWriter writer, CheckInOut check, EncoderContext ec) {

        ObjectId id = check.getId();
        String tipo = check.getTipo();
        ObjectId idPet = check.getIdPet();
        String nomePet = check.getNomePet();
        Date dataCheck = check.getDataCheck();

        
        Document doc = new Document();
        doc.put("_id", id);
        doc.put("tipo",tipo);
        doc.put("idPet",idPet);
        doc.put("nomePet", nomePet);
        doc.put("dataCheck", dataCheck);
        
       codec.encode(writer, doc, ec);
    }

    @Override
    public Class<CheckInOut> getEncoderClass() {
        //É preciso informar a classe a ser interpretada
        return CheckInOut.class;
    }

    @Override
    public CheckInOut decode(BsonReader reader, DecoderContext dc) {
        /*Aqui fizemos o processo inverso do decode, vamos dizer para o SPRING
        como o objeto será retornado*/
        Document doc = codec.decode(reader, dc);
        CheckInOut check = new CheckInOut();
        check.setId(doc.getObjectId("_id"));
        check.setTipo(doc.getString("tipo"));
        check.setIdPet(doc.getObjectId("idPet"));
        check.setNomePet(doc.getString("nomePet"));
        check.setDataCheck(doc.getDate("dataCheck"));


        return check;
    }    
}
