package io.deki.afopwn.repository;

import io.deki.afopwn.dto.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
}
