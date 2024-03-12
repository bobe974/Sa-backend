package com.etienne.sabackend.entites;

import com.etienne.sabackend.enums.TypeAvis;
import jakarta.persistence.*;
import org.springframework.context.annotation.Primary;

@Entity
@Table(name = "SENTIMENT")
public class Sentiment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String avis;

    private TypeAvis type;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    /* Chaque client peut être associé à zéro ou un avis (0..1).
       Chaque avis est associé à un client (1).
       MCD: Client--1,1--relation--0,*--Sentiment
       * - CascadeType.PERSIST : Cette option permet de propager l'opération de persistance
 *   (ajout) de l'entité parente (dans ce cas, l'entité Sentiment) à l'entité enfant
 *   (dans ce cas, l'entité Client). Lorsque vous enregistrez un nouveau Sentiment,
 *   le Client associé sera également enregistré.
 *
 * - CascadeType.MERGE : Cette option permet de propager l'opération de fusion
 *   (mise à jour) de l'entité parente à l'entité enfant. Si vous mettez à jour
 *   un Sentiment existant et que le Client associé a également été modifié,
 *   la mise à jour sera appliquée au Client.
 *
     */
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    public Sentiment(){

    }
    public Sentiment(int id, String avis){
        this.id = id;
        this.avis = avis;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvis() {
        return avis;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }

    public TypeAvis getType() {
        return type;
    }

    public void setType(TypeAvis type) {
        this.type = type;
    }
}
