import connection.Worker;
import repository.Operation;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hammer on 2018/4/29.
 */
public class Main {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Operation.initValues();
        for (int i = 0; i < 5; i++) {
            Worker.startTask(i + 2);
            int failCounter = Operation.failCounter.get();
            log.info("fail time is [{}]", failCounter);
            Operation.failCounter = new AtomicInteger(0);
        }
    }

    /**
     * isolation level read uncommitted
     *
     * 50 thread try 100 time/thread
     *
     * using c3p0 connection pool
     *
     [main] INFO connection.Worker - start testing  [1525005471495]
     [MLog-Init-Reporter] INFO com.mchange.v2.log.MLog - MLog clients using slf4j logging.
     [pool-3-thread-2] INFO com.mchange.v2.c3p0.C3P0Registry - Initializing c3p0-0.9.5.2 [built 08-December-2015 22:06:04 -0800; debug? true; trace: 10]
     [pool-3-thread-50] INFO com.mchange.v2.c3p0.impl.AbstractPoolBackedDataSource - Initializing c3p0 pool... com.mchange.v2.c3p0.ComboPooledDataSource [ acquireIncrement -> 5, acquireRetryAttempts -> 30, acquireRetryDelay -> 1000, autoCommitOnClose -> false, automaticTestTable -> null, breakAfterAcquireFailure -> false, checkoutTimeout -> 0, connectionCustomizerClassName -> null, connectionTesterClassName -> com.mchange.v2.c3p0.impl.DefaultConnectionTester, contextClassLoaderSource -> caller, dataSourceName -> 1bqq1oi9v4twbli18eznmj|2457beeb, debugUnreturnedConnectionStackTraces -> false, description -> null, driverClass -> com.mysql.jdbc.Driver, extensions -> {}, factoryClassLocation -> null, forceIgnoreUnresolvedTransactions -> false, forceSynchronousCheckins -> false, forceUseNamedDriverClass -> false, identityToken -> 1bqq1oi9v4twbli18eznmj|2457beeb, idleConnectionTestPeriod -> 0, initialPoolSize -> 3, jdbcUrl -> jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF8, maxAdministrativeTaskTime -> 0, maxConnectionAge -> 0, maxIdleTime -> 0, maxIdleTimeExcessConnections -> 0, maxPoolSize -> 20, maxStatements -> 180, maxStatementsPerConnection -> 0, minPoolSize -> 5, numHelperThreads -> 3, preferredTestQuery -> null, privilegeSpawnedThreads -> false, properties -> {user=******, password=******}, propertyCycle -> 0, statementCacheNumDeferredCloseThreads -> 0, testConnectionOnCheckin -> false, testConnectionOnCheckout -> false, unreturnedConnectionTimeout -> 0, userOverrides -> {}, usesTraditionalReflectiveProxies -> false ]
     [pool-3-thread-50] WARN com.mchange.v2.resourcepool.BasicResourcePool - Bad pool size config, start 3 < min 5. Using 5 as start.
     [main] INFO connection.Worker - end testing  [1525005477193]
     [main] INFO connection.Worker - cost milliseconds  [5698]
     [main] INFO connection.Worker - Finished all threads
     [main] INFO Main - fail time is [5000]
     [main] INFO connection.Worker - start testing  [1525005477194]
     [main] INFO connection.Worker - end testing  [1525005481183]
     [main] INFO connection.Worker - cost milliseconds  [3989]
     [main] INFO connection.Worker - Finished all threads
     [main] INFO Main - fail time is [4996]
     [main] INFO connection.Worker - start testing  [1525005481184]
     [main] INFO connection.Worker - end testing  [1525005484906]
     [main] INFO connection.Worker - cost milliseconds  [3722]
     [main] INFO connection.Worker - Finished all threads
     [main] INFO Main - fail time is [4999]
     [main] INFO connection.Worker - start testing  [1525005484907]
     [main] INFO connection.Worker - end testing  [1525005488354]
     [main] INFO connection.Worker - cost milliseconds  [3447]
     [main] INFO connection.Worker - Finished all threads
     [main] INFO Main - fail time is [4993]
     [main] INFO connection.Worker - start testing  [1525005488354]
     [main] INFO connection.Worker - end testing  [1525005491791]
     [main] INFO connection.Worker - cost milliseconds  [3437]
     [main] INFO connection.Worker - Finished all threads
     [main] INFO Main - fail time is [4999]




     */


    /**
     *
     * isolation level read uncommitted -- for update
     *
     * 50 thread try 100 time/thread
     *
     * using c3p0 connection pool
     *
     [main] INFO connection.Worker - start testing  [1525005666475]
     [MLog-Init-Reporter] INFO com.mchange.v2.log.MLog - MLog clients using slf4j logging.
     [pool-3-thread-1] INFO com.mchange.v2.c3p0.C3P0Registry - Initializing c3p0-0.9.5.2 [built 08-December-2015 22:06:04 -0800; debug? true; trace: 10]
     [pool-3-thread-1] INFO com.mchange.v2.c3p0.impl.AbstractPoolBackedDataSource - Initializing c3p0 pool... com.mchange.v2.c3p0.ComboPooledDataSource [ acquireIncrement -> 5, acquireRetryAttempts -> 30, acquireRetryDelay -> 1000, autoCommitOnClose -> false, automaticTestTable -> null, breakAfterAcquireFailure -> false, checkoutTimeout -> 0, connectionCustomizerClassName -> null, connectionTesterClassName -> com.mchange.v2.c3p0.impl.DefaultConnectionTester, contextClassLoaderSource -> caller, dataSourceName -> 1bqq1oi9v4u0i041bmsun6|10a8dc99, debugUnreturnedConnectionStackTraces -> false, description -> null, driverClass -> com.mysql.jdbc.Driver, extensions -> {}, factoryClassLocation -> null, forceIgnoreUnresolvedTransactions -> false, forceSynchronousCheckins -> false, forceUseNamedDriverClass -> false, identityToken -> 1bqq1oi9v4u0i041bmsun6|10a8dc99, idleConnectionTestPeriod -> 0, initialPoolSize -> 3, jdbcUrl -> jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF8, maxAdministrativeTaskTime -> 0, maxConnectionAge -> 0, maxIdleTime -> 0, maxIdleTimeExcessConnections -> 0, maxPoolSize -> 20, maxStatements -> 180, maxStatementsPerConnection -> 0, minPoolSize -> 5, numHelperThreads -> 3, preferredTestQuery -> null, privilegeSpawnedThreads -> false, properties -> {user=******, password=******}, propertyCycle -> 0, statementCacheNumDeferredCloseThreads -> 0, testConnectionOnCheckin -> false, testConnectionOnCheckout -> false, unreturnedConnectionTimeout -> 0, userOverrides -> {}, usesTraditionalReflectiveProxies -> false ]
     [pool-3-thread-4] WARN com.mchange.v2.resourcepool.BasicResourcePool - Bad pool size config, start 3 < min 5. Using 5 as start.
     [main] INFO connection.Worker - end testing  [1525005671104]
     [main] INFO connection.Worker - cost milliseconds  [4629]
     [main] INFO connection.Worker - Finished all threads
     [main] INFO Main - fail time is [0]
     [main] INFO connection.Worker - start testing  [1525005671105]
     [main] INFO connection.Worker - end testing  [1525005674410]
     [main] INFO connection.Worker - cost milliseconds  [3305]
     [main] INFO connection.Worker - Finished all threads
     [main] INFO Main - fail time is [0]
     [main] INFO connection.Worker - start testing  [1525005674410]
     [main] INFO connection.Worker - end testing  [1525005677481]
     [main] INFO connection.Worker - cost milliseconds  [3071]
     [main] INFO connection.Worker - Finished all threads
     [main] INFO Main - fail time is [0]
     [main] INFO connection.Worker - start testing  [1525005677482]
     [main] INFO connection.Worker - end testing  [1525005680246]
     [main] INFO connection.Worker - cost milliseconds  [2764]
     [main] INFO connection.Worker - Finished all threads
     [main] INFO Main - fail time is [0]
     [main] INFO connection.Worker - start testing  [1525005680247]
     [main] INFO connection.Worker - end testing  [1525005683054]
     [main] INFO connection.Worker - cost milliseconds  [2807]
     [main] INFO connection.Worker - Finished all threads
     [main] INFO Main - fail time is [0]
     */



    /**
     *  isolation level read uncommitted    -- for update
     *
     *  200 thread try 100 time/thread
     *
     *  using c3p0 connection pool
     *
     *
     [main] INFO connection.Worker - start testing  [1525006016069]
     [MLog-Init-Reporter] INFO com.mchange.v2.log.MLog - MLog clients using slf4j logging.
     [pool-3-thread-2] INFO com.mchange.v2.c3p0.C3P0Registry - Initializing c3p0-0.9.5.2 [built 08-December-2015 22:06:04 -0800; debug? true; trace: 10]
     [pool-3-thread-2] INFO com.mchange.v2.c3p0.impl.AbstractPoolBackedDataSource - Initializing c3p0 pool... com.mchange.v2.c3p0.ComboPooledDataSource [ acquireIncrement -> 5, acquireRetryAttempts -> 30, acquireRetryDelay -> 1000, autoCommitOnClose -> false, automaticTestTable -> null, breakAfterAcquireFailure -> false, checkoutTimeout -> 0, connectionCustomizerClassName -> null, connectionTesterClassName -> com.mchange.v2.c3p0.impl.DefaultConnectionTester, contextClassLoaderSource -> caller, dataSourceName -> 1bqq1oi9v4u7zs6v5byup|76436a13, debugUnreturnedConnectionStackTraces -> false, description -> null, driverClass -> com.mysql.jdbc.Driver, extensions -> {}, factoryClassLocation -> null, forceIgnoreUnresolvedTransactions -> false, forceSynchronousCheckins -> false, forceUseNamedDriverClass -> false, identityToken -> 1bqq1oi9v4u7zs6v5byup|76436a13, idleConnectionTestPeriod -> 0, initialPoolSize -> 3, jdbcUrl -> jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF8, maxAdministrativeTaskTime -> 0, maxConnectionAge -> 0, maxIdleTime -> 0, maxIdleTimeExcessConnections -> 0, maxPoolSize -> 20, maxStatements -> 180, maxStatementsPerConnection -> 0, minPoolSize -> 5, numHelperThreads -> 3, preferredTestQuery -> null, privilegeSpawnedThreads -> false, properties -> {user=******, password=******}, propertyCycle -> 0, statementCacheNumDeferredCloseThreads -> 0, testConnectionOnCheckin -> false, testConnectionOnCheckout -> false, unreturnedConnectionTimeout -> 0, userOverrides -> {}, usesTraditionalReflectiveProxies -> false ]
     [pool-3-thread-3] WARN com.mchange.v2.resourcepool.BasicResourcePool - Bad pool size config, start 3 < min 5. Using 5 as start.
     [main] INFO connection.Worker - end testing  [1525006034047]
     [main] INFO connection.Worker - cost milliseconds  [17978]
     [main] INFO connection.Worker - Finished all threads
     [main] INFO Main - fail time is [0]
     [main] INFO connection.Worker - start testing  [1525006034048]
     [main] INFO connection.Worker - end testing  [1525006048038]
     [main] INFO connection.Worker - cost milliseconds  [13990]
     [main] INFO connection.Worker - Finished all threads
     [main] INFO Main - fail time is [0]
     [main] INFO connection.Worker - start testing  [1525006048038]
     [main] INFO connection.Worker - end testing  [1525006061714]
     [main] INFO connection.Worker - cost milliseconds  [13676]
     [main] INFO connection.Worker - Finished all threads
     [main] INFO Main - fail time is [0]
     [main] INFO connection.Worker - start testing  [1525006061714]
     [main] INFO connection.Worker - end testing  [1525006074947]
     [main] INFO connection.Worker - cost milliseconds  [13233]
     [main] INFO connection.Worker - Finished all threads
     [main] INFO Main - fail time is [0]
     [main] INFO connection.Worker - start testing  [1525006074948]
     [main] INFO connection.Worker - end testing  [1525006088155]
     [main] INFO connection.Worker - cost milliseconds  [13207]
     [main] INFO connection.Worker - Finished all threads
     [main] INFO Main - fail time is [0]
     */


    /**
     *  isolation level read uncommitted
     *
     *  200 thread try 100 time/thread
     *
     *  using c3p0 connection pool
     *
     [main] INFO connection.Worker - start testing  [1525005872381]
     [MLog-Init-Reporter] INFO com.mchange.v2.log.MLog - MLog clients using slf4j logging.
     [pool-3-thread-1] INFO com.mchange.v2.c3p0.C3P0Registry - Initializing c3p0-0.9.5.2 [built 08-December-2015 22:06:04 -0800; debug? true; trace: 10]
     [pool-3-thread-200] INFO com.mchange.v2.c3p0.impl.AbstractPoolBackedDataSource - Initializing c3p0 pool... com.mchange.v2.c3p0.ComboPooledDataSource [ acquireIncrement -> 5, acquireRetryAttempts -> 30, acquireRetryDelay -> 1000, autoCommitOnClose -> false, automaticTestTable -> null, breakAfterAcquireFailure -> false, checkoutTimeout -> 0, connectionCustomizerClassName -> null, connectionTesterClassName -> com.mchange.v2.c3p0.impl.DefaultConnectionTester, contextClassLoaderSource -> caller, dataSourceName -> 1bqq1oi9v4u4wzi1htdl2t|3d592425, debugUnreturnedConnectionStackTraces -> false, description -> null, driverClass -> com.mysql.jdbc.Driver, extensions -> {}, factoryClassLocation -> null, forceIgnoreUnresolvedTransactions -> false, forceSynchronousCheckins -> false, forceUseNamedDriverClass -> false, identityToken -> 1bqq1oi9v4u4wzi1htdl2t|3d592425, idleConnectionTestPeriod -> 0, initialPoolSize -> 3, jdbcUrl -> jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF8, maxAdministrativeTaskTime -> 0, maxConnectionAge -> 0, maxIdleTime -> 0, maxIdleTimeExcessConnections -> 0, maxPoolSize -> 20, maxStatements -> 180, maxStatementsPerConnection -> 0, minPoolSize -> 5, numHelperThreads -> 3, preferredTestQuery -> null, privilegeSpawnedThreads -> false, properties -> {user=******, password=******}, propertyCycle -> 0, statementCacheNumDeferredCloseThreads -> 0, testConnectionOnCheckin -> false, testConnectionOnCheckout -> false, unreturnedConnectionTimeout -> 0, userOverrides -> {}, usesTraditionalReflectiveProxies -> false ]
     [pool-3-thread-200] WARN com.mchange.v2.resourcepool.BasicResourcePool - Bad pool size config, start 3 < min 5. Using 5 as start.
     [main] INFO connection.Worker - end testing  [1525005900460]
     [main] INFO connection.Worker - cost milliseconds  [28079]
     [main] INFO connection.Worker - Finished all threads
     [main] INFO Main - fail time is [19987]
     [main] INFO connection.Worker - start testing  [1525005900460]
     [main] INFO connection.Worker - end testing  [1525005920496]
     [main] INFO connection.Worker - cost milliseconds  [20036]
     [main] INFO connection.Worker - Finished all threads
     [main] INFO Main - fail time is [19999]
     [main] INFO connection.Worker - start testing  [1525005920496]
     [main] INFO connection.Worker - end testing  [1525005939436]
     [main] INFO connection.Worker - cost milliseconds  [18940]
     [main] INFO connection.Worker - Finished all threads
     [main] INFO Main - fail time is [19993]
     [main] INFO connection.Worker - start testing  [1525005939436]
     [main] INFO connection.Worker - end testing  [1525005957619]
     [main] INFO connection.Worker - cost milliseconds  [18183]
     [main] INFO connection.Worker - Finished all threads
     [main] INFO Main - fail time is [19994]
     [main] INFO connection.Worker - start testing  [1525005957619]
     [main] INFO connection.Worker - end testing  [1525005974968]
     [main] INFO connection.Worker - cost milliseconds  [17349]
     [main] INFO connection.Worker - Finished all threads
     [main] INFO Main - fail time is [19992]
     */
}
