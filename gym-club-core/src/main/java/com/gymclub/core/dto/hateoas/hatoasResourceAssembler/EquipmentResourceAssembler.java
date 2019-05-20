package com.gymclub.core.dto.hateoas.hatoasResourceAssembler;


import com.gymclub.core.controller.HateoasController;
import com.gymclub.core.domain.secondary.Equipment;
import com.gymclub.core.dto.hateoas.EquipmentResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class EquipmentResourceAssembler extends ResourceAssemblerSupport<Equipment, EquipmentResource> {

    public EquipmentResourceAssembler() {
        super(HateoasController.class, EquipmentResource.class);
    }

    @Override
    public EquipmentResource toResource(Equipment equipment) {
        EquipmentResource resource = createResourceWithId(equipment.getId(), equipment);
        return resource;
    }

    @Override
    protected EquipmentResource instantiateResource(Equipment entity) {
        return new EquipmentResource(entity);
    }
}
