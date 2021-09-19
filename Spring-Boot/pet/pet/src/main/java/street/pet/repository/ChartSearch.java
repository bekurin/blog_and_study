package street.pet.repository;

import lombok.Getter;
import street.pet.domain.ChartStatus;

@Getter
public class ChartSearch {

    private ChartStatus status;
    private String petName;

    public ChartSearch(ChartStatus status, String petName) {
        this.status = status;
        this.petName = petName;
    }

    public ChartSearch setPetName(String petName){
        this.petName = petName;
        return this;
    }

    public ChartSearch setStatus(ChartStatus status){
        this.status = status;
        return this;
    }
}
