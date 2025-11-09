package com.proyectos.persistence.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.proyectos.model.Categoria;
import com.proyectos.persistence.MongoConexion;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoriaDaoMongo implements CategoriaDao {

    private final MongoCollection<Document> collection;

    public CategoriaDaoMongo() {
        MongoDatabase db = MongoConexion.getDatabase();
        this.collection = db.getCollection("categorias");
    }

    @Override
    public Categoria save(Categoria categoria) {
        Document doc = new Document()
                .append("nombre", categoria.getNombre())
                .append("descripcion", categoria.getDescripcion());
        collection.insertOne(doc);
        categoria.setId((long) doc.getObjectId("_id").getTimestamp()); // ID simbólico
        return categoria;
    }

    @Override
    public Optional<Categoria> findById(Long id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        if (doc == null) return Optional.empty();
        Categoria c = new Categoria(
                id,
                doc.getString("nombre"),
                doc.getString("descripcion")
        );
        return Optional.of(c);
    }

    @Override
    public List<Categoria> findAll() {
        List<Categoria> categorias = new ArrayList<>();
        for (Document doc : collection.find()) {
            Categoria c = new Categoria(
                    null,
                    doc.getString("nombre"),
                    doc.getString("descripcion")
            );
            categorias.add(c);
        }
        return categorias;
    }

    @Override
    public Categoria update(Categoria categoria) {
        Document update = new Document("$set", new Document()
                .append("nombre", categoria.getNombre())
                .append("descripcion", categoria.getDescripcion()));
        collection.updateOne(Filters.eq("_id", categoria.getId()), update);
        return categoria;
    }

    @Override
    public boolean delete(Long id) {
        return collection.deleteOne(Filters.eq("_id", id)).getDeletedCount() > 0;
    }
}
