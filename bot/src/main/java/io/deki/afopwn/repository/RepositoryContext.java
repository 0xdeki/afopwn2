package io.deki.afopwn.repository;

import io.deki.afopwn.ai.AfoClient;
import io.deki.afopwn.cache.Cache;
import io.deki.afopwn.commons.Random;
import io.deki.afopwn.commons.Time;
import io.deki.afopwn.dto.Account;
import io.deki.afopwn.task.account.Login;
import io.deki.afopwn.task.account.Register;
import io.deki.afopwn.task.account.Rename;
import io.deki.afopwn.task.arena.Fight;
import io.deki.afopwn.task.task.DailyTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class RepositoryContext {

    public static AccountRepository accountRepository;

    public static Cache cache;

    private Logger logger = LoggerFactory.getLogger(RepositoryContext.class);

    private ExecutorService executor = Executors.newCachedThreadPool();

    @Autowired
    public RepositoryContext(AccountRepository accountRepository) throws IOException {
        RepositoryContext.accountRepository = accountRepository;
        cache = new Cache();
        cache.load();

        for (Account account : accountRepository.findAll()) {
            executor.execute(new AfoClient(account));
            Time.sleep(2000);
        }
    }

}
