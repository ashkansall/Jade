# Original JADE Lab 1

This is a separate copy of the original JADE tutorial project. The Java source code is unchanged.

## Run from VS Code

Use either method:

- Open **Terminal > Run Task**, then select **JADE: Run**. Do not type the task name into PowerShell.
- Or enter `./run-jade.cmd` in the VS Code terminal.

Then:

3. Wait for the JADE Remote Agent Management window.
4. Dismiss the greeting popup.
5. Right-click the existing `UserAgent` and select **Send Message**.
6. Use:
   - Communicative act: `request`
   - Content: an English word, such as `computer`
   - Ontology: `wordnet` or `dictionary`
7. Leave the other fields unchanged and click **OK**.

Stop the application with the trash-can button in VS Code's Terminal panel, or focus the terminal and press **Ctrl+C**.

## Useful shortcuts

- **Ctrl+Shift+B** compiles the project.
- **Terminal > Run Task > JADE: Run** compiles and runs it.
- `./compile-jade.cmd` compiles from the terminal.
- `./run-jade.cmd` compiles and runs from the terminal.

The external `dict.org` endpoint used by this old sample may no longer return definitions. That does not prevent the local JADE platform and agent messaging from running.
