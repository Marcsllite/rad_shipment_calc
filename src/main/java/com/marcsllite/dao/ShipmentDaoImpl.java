package com.marcsllite.dao;

import com.marcsllite.model.db.ShipmentModel;

public class ShipmentDaoImpl extends AbstractDao<ShipmentModel, Long> {
    public ShipmentModel getShipment(Long id) {
        return findById(id);
    }
}
