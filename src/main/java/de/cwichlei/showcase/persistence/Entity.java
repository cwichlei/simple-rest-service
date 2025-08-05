package de.cwichlei.showcase.persistence;

import de.cwichlei.showcase.rest.Request;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@jakarta.persistence.Entity
@Getter
@NoArgsConstructor
@ToString
public class Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    public Entity(String text) {
        this.text = text;
    }

    public Entity(Request request){
        this.text = request.getText();
    }
}
