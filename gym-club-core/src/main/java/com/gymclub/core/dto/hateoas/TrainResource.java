package com.gymclub.core.dto.hateoas;

import com.gymclub.core.domain.primary.Trainer;
import org.springframework.hateoas.ResourceSupport;

public class TrainResource extends ResourceSupport {

    private final Trainer trainer;

    public TrainResource(Trainer trainer) {
        this.trainer = trainer;
    }

    public Trainer getTrainer() {
        return trainer;
    }
}

