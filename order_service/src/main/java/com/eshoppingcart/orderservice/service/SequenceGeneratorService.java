
package com.eshoppingcart.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.eshoppingcart.orderservice.model.DBSequence;



@Service
public class SequenceGeneratorService {

	private MongoOperations mongoOperations;

	@Autowired
	public SequenceGeneratorService(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	public int generateSequence(String seqName) {
		DBSequence counter = mongoOperations.findAndModify(Query.query(Criteria.where("_id").is(seqName)),
				new Update().inc("seq", 1), FindAndModifyOptions.options().returnNew(true).upsert(true),
				DBSequence.class);

		return (int) (counter != null ? counter.getSeq() : 1);
	}
}
