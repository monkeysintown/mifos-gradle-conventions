package org.mifos.conventions.cli;

import dev.tamboui.toolkit.app.ToolkitRunner;
import io.pebbletemplates.pebble.PebbleEngine;
import org.mifos.conventions.cli.model.MifosTemplate;
import tools.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static dev.tamboui.toolkit.Toolkit.markupText;

class Main {
    static void main(String[] args) {
        var engine = new PebbleEngine.Builder().build();

        var start = Paths.get("/home/spaddo/workspace/mifos/mifos-conventions-gradle/modules/project/boot/reactive/src/main/template/core");

        try (var stream = Files.walk(start)) {
            stream.filter(Files::isRegularFile)
                    .forEach(System.out::println);


            var mapper = new YAMLMapper();

            var template = mapper.readValue(new File("/home/spaddo/workspace/mifos/mifos-conventions-gradle/modules/project/boot/reactive/src/main/template/template.yml"), MifosTemplate.class);

            System.out.println(mapper.writeValueAsString(template));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (var runner = ToolkitRunner.create()) {
            runner.run(() -> markupText("Hello, [red]TamboUI[/red]! Press [blue]q[/] to exit!"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
