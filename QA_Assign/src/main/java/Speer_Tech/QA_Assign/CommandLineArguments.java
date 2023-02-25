package Speer_Tech.QA_Assign;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;
import lombok.Builder;
import lombok.Data;

/**
 * Command line arguments passed to the application.
 */
@Builder(toBuilder = true)
@Data
public class CommandLineArguments {
    @Builder.Default
    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Builder.Default
    @Parameter(names = { "--number1", "-x" }, description = "Input number 1")
    private Integer x = 1;

    @Builder.Default
    @Parameter(names = { "--number2", "-y" }, description = "Input number 2")
    private Integer y = 1;

    @Builder.Default
    @Parameter(names = "--operation", description = "Math operation to perform")
    private String operation = "add";

    @Builder.Default
    @Parameter(names = "--debug", description = "Debug mode")
    private boolean debug = false;
}
