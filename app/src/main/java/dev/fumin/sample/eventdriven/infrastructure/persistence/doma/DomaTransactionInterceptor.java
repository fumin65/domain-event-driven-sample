package dev.fumin.sample.eventdriven.infrastructure.persistence.doma;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.tx.TransactionManager;

public class DomaTransactionInterceptor implements MethodInterceptor {

    private final Config config;

    public DomaTransactionInterceptor(Config config) {
        this.config = config;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        TransactionManager tm = config.getTransactionManager();
        try {
            return tm.required(() -> {
                try {
                    return invocation.proceed();
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
            });
        } catch (Exception e) {
            throw e.getCause();
        }
    }

}
