package Speer_Tech.QA_Assign;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

/**
 * Application main class.
 */
@Log4j2
public class Application {

    private Calculator calculator;

    public static Application INSTANCE;

    public static void main(final String[] args) {
        log.info("Starting application...");
        final CommandLineArguments arguments = CommandLineArguments.builder().build();
        final JCommander commander = JCommander.newBuilder()
                .addObject(arguments)
                .programName("myapp")
                .build();

        try {
            commander.parse(args);
            assignInstance(new Application()).run(arguments);
        } catch (final ParameterException ex) {
            log.error("Error parsing arguments: {}", args, ex);
            System.err.println(ex.getMessage());
            commander.usage();
        }
        log.info("Exited application");
    }

    protected static Application assignInstance(final Application instance) {
        if (INSTANCE == null) {
            INSTANCE = instance;
        }
        return INSTANCE;
    }

    public void run(final CommandLineArguments arguments) {
        log.info("Started application");

        if (calculator == null) {
            calculator = createCalculator();
        }

        if (StringUtils.equalsIgnoreCase("add", arguments.getOperation())) {
            final int result = calculator.add(arguments.getX(), arguments.getY());
            calculator.save(result);
        } else if (StringUtils.equalsIgnoreCase("multiply", arguments.getOperation())) {
            final int result = calculator.multiply(arguments.getX(), arguments.getY());
            calculator.save(result);
        }

        log.info("Exiting application...");
    }

    protected Calculator createCalculator() {
        return new Calculator() {
            @Override
            public int add(int x, int y) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int multiply(int x, int y) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void save(int x) {
                throw new UnsupportedOperationException();
            }
        };
    }
}
