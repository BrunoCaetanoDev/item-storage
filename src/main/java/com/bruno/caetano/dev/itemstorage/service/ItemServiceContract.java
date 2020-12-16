package com.bruno.caetano.dev.itemstorage.service;

import com.bruno.caetano.dev.itemstorage.entity.model.Item;
import java.math.BigInteger;

public interface ItemServiceContract extends CrudServiceContract<Item> {


	void restock(String id, BigInteger qty);

	void dispatch(String id, BigInteger qty);

}
