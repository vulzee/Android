package serializerteam.serializer.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by xxx on 2017-05-01.
 */

public class CastDto {

    @SerializedName("person")
    @Expose
    private PersonDto person;
    @SerializedName("character")
    @Expose
    private CharacterDto character;

    public PersonDto getPerson() {
        return person;
    }

    public void setPerson(PersonDto person) {
        this.person = person;
    }

    public CharacterDto getCharacter() {
        return character;
    }

    public void setCharacter(CharacterDto character) {
        this.character = character;
    }

}