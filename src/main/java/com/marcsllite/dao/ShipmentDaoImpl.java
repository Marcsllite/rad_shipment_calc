package com.marcsllite.dao;

import com.marcsllite.model.db.ShipmentsModel;

public class ShipmentDaoImpl extends AbstractDao<ShipmentsModel, Long> {
    public ShipmentsModel getShipment(Long id) {
        return findById(id);
    }
}
