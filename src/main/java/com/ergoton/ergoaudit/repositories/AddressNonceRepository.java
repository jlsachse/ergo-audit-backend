package com.ergoton.ergoaudit.repositories;

import com.ergoton.ergoaudit.model.AddressNonce;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressNonceRepository extends MongoRepository<AddressNonce, String> {
}
