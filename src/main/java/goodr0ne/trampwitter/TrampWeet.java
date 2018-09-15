package goodr0ne.trampwitter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TrampWeet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String json;

    public TrampWeet() {
        id = Long.parseLong("1337");
        json = "default constructed";
    }

    public TrampWeet(String json) {
        this.json = json;
    }

    public Long getId() {
        return id;
    }

    public String getJson() {
        return json;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setJson(String json) {
        this.json = json;
    }
}