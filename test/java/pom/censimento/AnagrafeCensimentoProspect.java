package pom.censimento;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.ArrayList;
import java.util.List;

public class AnagrafeCensimentoProspect {

    private FrameLocator frame;
    private Page page;


    public AnagrafeCensimentoProspect(Page page) {
        this.page = page;
        this.frame = page.frameLocator(".dbx-quadrotto-router-iframe");
    }

    public String taxCode = "//input[@formcontrolname='taxNumber']";
    public String name = "input[formcontrolname='name']";
    public String lastname = "input[formcontrolname='lastName']";
    public String phone = "input[formcontrolname='phoneNumber']";
    public String email = "input[formcontrolname='email']";

    private String naturaGiuridicaSesso = ".calc-gender";

    private String msgCompletaCampi = "//h5[text()='Completa tutti i campi prima di procedere.']";
    private String msg = "//div[@class='ion-ds-tooltip-container-inner ion-ds-undefined ng-scope']";
    private String invalidClass = "//input[@class='ion-input form-control ng-invalid ng-dirty ng-touched']";
    private String invalidClass2 = "//input[@class='ion-input form-control ng-dirty ng-touched ng-invalid']";
    private String invalidClassCognome = "ion-input form-control ng-invalid ng-touched ng-dirty";

    private String annulla = "button[aria-label='Annulla']";
    private String prosegui = "button[aria-label='Prosegui']";
    private String censimentoProspect = "button[aria-label=\"Censimento prospect\"]";

    private String borderColor = "warning_filledCodice Fiscale";

    private String avvia = "button[aria-label='Avvia verifica']";

    private String documentSign = "//div[@class='docs-signing-container']";

    private String eventi = "//span[text()='Eventi']";
    private String liste = "//span[text()='Liste']";

    private String censimentoProspectLabel = "//div[@class='form-title']//span[text()='Censimento prospect']";
    private String workflow = "//div[@class='ion-ds-segmented-control-group-container segmented-control-group-container segmented-control-group-container-horizontal ion-ds-md ion-ds-disabled']";

    private String exit = "button[aria-label='close']";

    public void clickCensimentoProspect(){
        try {
            frame.locator(censimentoProspect).click();
        } catch (Exception e) {
            System.err.println("Button with aria-label 'Censimento prospect' was not found or could not be clicked.");
            throw e;
        }
    }

    public void clickAnnulla(){
        try {
            frame.locator(annulla).click();
        } catch (Exception e) {
            System.err.println("Button with aria-label 'Annulla' was not found or could not be clicked.");
            throw e;
        }
    }

    public void clickAvviaVerifica(){
        try {
            frame.locator(avvia).click();
        } catch (Exception e) {
            System.err.println("Button with aria-label 'Avvia verifica' was not found or could not be clicked.");
            throw e;
        }
    }

    public void clickProsegui(){
        try {
            frame.locator(prosegui).click();
        } catch (Exception e) {
            System.err.println("Button with aria-label 'Prosegui' was not found or could not be clicked.");
            throw e;
        }
    }

    // Method to verify the presence of all locators
    public List<String> verificaPresenzaCampi() {
        List<String> missingLocators = new ArrayList<>();

        if (!isElementPresent(taxCode)) {
            missingLocators.add("Tax Code");
        }
        if (!isElementPresent(name)) {
            missingLocators.add("Name");
        }
        if (!isElementPresent(lastname)) {
            missingLocators.add("Last Name");
        }
        if (!isElementPresent(phone)) {
            missingLocators.add("Phone");
        }
        if (!isElementPresent(email)) {
            missingLocators.add("Email");
        }

        return missingLocators;
    }

    // Helper method to check if a specific element is present
    private boolean isElementPresent(String locator) {
        Locator element = frame.locator(locator);
        return element.count() > 0;
    }


    //////
    public boolean isTaxCodePresent() {
        return frame.locator(taxCode).isVisible();
    }

    public boolean isNamePresent() {
        return frame.locator(name).isVisible();
    }

    public boolean isLastNamePresent() {
        return frame.locator(lastname).isVisible();
    }

    public boolean isPhonePresent() {
        return frame.locator(phone).isVisible();
    }

    public boolean isEmailPresent() {
        return frame.locator(email).isVisible();
    }

    //////

    public String validazioneCodiceFiscale(String codiceFiscale) throws InterruptedException {
        try {
            frame.locator(taxCode).type(codiceFiscale);
            page.keyboard().press("Tab");
            frame.locator(invalidClass).or(frame.locator(invalidClass2)).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED).setTimeout(5000));
            //page.locator(lastname).click();
            frame.locator(taxCode).hover();

            frame.locator(msg).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED).setTimeout(5000));

            String textContent = frame.locator(msg).textContent();
            /*if (textContent != null) {
                textContent = textContent.trim().toLowerCase();
            }*/
            return textContent;
        } catch (TimeoutError e) {
            // Return this message if the tooltip is not visible within the timeout
            return "ToolTip non viene visualizzato";
        }
    }


    public String validazioneCodiceFiscaleEsistente(String codiceFiscale, String optionType) {
        switch (optionType) {
            case "1": // EXISTENT
                try {
                    frame.locator(taxCode).type(codiceFiscale);
                    page.keyboard().press("Tab");
                    frame.locator(invalidClass).or(frame.locator(invalidClass2))
                            .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED).setTimeout(5000));
                    frame.locator(taxCode).hover();
                    frame.locator(msg)
                            .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED).setTimeout(5000));

                    String textContent = frame.locator(msg).textContent();
                    return textContent != null ? textContent : null;

                } catch (TimeoutError e) {
                    // Return this message if the tooltip is not visible within the timeout
                    return "ToolTip non viene visualizzato";
                }

            case "2": // VALID
                try {
                    // Type the codiceFiscale and press Tab
                    frame.locator(taxCode).type(codiceFiscale);
                    frame.locator(taxCode).press("Tab");

                    // Wait for the element to be visible
                    frame.locator(naturaGiuridicaSesso)
                            .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));

                    // Get the text content of the element
                    String textContent = frame.locator(naturaGiuridicaSesso).textContent();

                    if (textContent == null) {
                        return null;
                    }

                    // Find the starting index of ngsesso in textContent
                    int startIndex = textContent.indexOf("Soggetto");
                    if (startIndex != -1) {
                        // Return the substring that matches ngsesso
                        return textContent.substring(startIndex, startIndex + "Soggetto".length());
                    }

                    return null;

                } catch (TimeoutError e) {
                    // Return this message if the tooltip is not visible within the timeout
                    return "ToolTip non viene visualizzato";
                }

            default:
                throw new IllegalArgumentException("Unsupported option type: " + optionType);
        }
    }

    public String validazioneCodiceFiscaleEsistenteIncolla(String codiceFiscale, String optionType) {
        switch (optionType) {
            case "1": // EXISTENT
                try {
                    page.evaluate("codiceFiscale => navigator.clipboard.writeText(codiceFiscale)", codiceFiscale);

                    // Focus on the taxCode locator
                    frame.locator(taxCode).focus();

                    // Paste the codiceFiscale from the clipboard
                    frame.locator(taxCode).press("Control+V");
                    page.keyboard().press("Tab");
                    frame.locator(invalidClass).or(frame.locator(invalidClass2))
                            .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED).setTimeout(5000));
                    frame.locator(taxCode).hover();
                    frame.locator(msg)
                            .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED).setTimeout(5000));

                    String textContent = frame.locator(msg).textContent();
                    return textContent != null ? textContent : null;

                } catch (TimeoutError e) {
                    // Return this message if the tooltip is not visible within the timeout
                    return "ToolTip non viene visualizzato";
                }

            case "2": // VALID
                try {
                    page.evaluate("codiceFiscale => navigator.clipboard.writeText(codiceFiscale)", codiceFiscale);

                    // Focus on the taxCode locator
                    frame.locator(taxCode).focus();

                    // Paste the codiceFiscale from the clipboard
                    frame.locator(taxCode).press("Control+V");
                    frame.locator(taxCode).press("Tab");

                    // Wait for the element to be visible
                    frame.locator(naturaGiuridicaSesso)
                            .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));

                    // Get the text content of the element
                    String textContent = frame.locator(naturaGiuridicaSesso).textContent();

                    if (textContent == null) {
                        return null;
                    }

                    // Find the starting index of ngsesso in textContent
                    int startIndex = textContent.indexOf("Soggetto");
                    if (startIndex != -1) {
                        // Return the substring that matches ngsesso
                        return textContent.substring(startIndex, startIndex + "Soggetto".length());
                    }

                    return null;

                } catch (TimeoutError e) {
                    // Return this message if the tooltip is not visible within the timeout
                    return "ToolTip non viene visualizzato";
                }

            default:
                throw new IllegalArgumentException("Unsupported option type: " + optionType);
        }
    }


    public String validazioneCognome_OLD(String cognome) {
        try {
            frame.locator(lastname).type(cognome);
            frame.locator(lastname).press("Tab");
            frame.locator(lastname).hover();
            frame.locator(msg).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));

            String textContent = frame.locator(msg).textContent();
            /*if (textContent != null) {
                textContent = textContent.trim().toLowerCase();
            }*/
            return textContent;
        } catch (TimeoutError e) {
            // Return this message if the tooltip is not visible within the timeout
            return "ToolTip non viene visualizzato";
        }
    }

    public String validazioneCognome(String cognome, String expectedValue) {
        try {
            // Step 1: Type the full input into the field
            frame.locator(lastname).type(cognome);
            frame.locator(lastname).press("Tab");

            // Step 2: Retrieve the current value in the input field
            String actualValue = frame.locator(lastname).inputValue().trim();

            // Step 3: Handle different expected cases
            // If the input is a numeric or special character string, the field should not allow them
            if (cognome.matches(".*[0-9!@#$%^&*()].*")) {
                // Check if invalid characters were accepted into the field
                if (!actualValue.equals(expectedValue)) {
                    return "Invalid Field Tooltip: UI should not allow entering invalid characters.";
                }
            }

            // Step 4: Handle the case where leading/trailing or consecutive spaces exist
            if (cognome.startsWith(" ") || cognome.endsWith(" ") || cognome.contains("  ")) {
                // Check for the "Invalid Field" tooltip
                frame.locator(lastname).hover();
                frame.locator(msg).waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(5000)
                );

                String textContent = frame.locator(msg).textContent();
                if (textContent != null) {
                    return textContent;
                }
            }

            // If no errors or tooltips, assume the input is valid
            return actualValue;

        } catch (TimeoutError e) {
            // Step 5: If no tooltip appears but an error was expected, return a default invalid message
            return "ToolTip non viene visualizzato";
        }
    }

    public String validazioneNome(String nome, String expectedValue) {
        try {
            // Step 1: Type the full input into the field
            frame.locator(name).type(nome);
            frame.locator(name).press("Tab");

            // Step 2: Retrieve the current value in the input field
            String actualValue = frame.locator(name).inputValue().trim();

            // Step 3: Handle different expected cases
            // If the input is a numeric or special character string, the field should not allow them
            if (nome.matches(".*[0-9!@#$%^&*()].*")) {
                // Check if invalid characters were accepted into the field
                if (!actualValue.equals(expectedValue)) {
                    return "Invalid Field Tooltip: UI should not allow entering invalid characters.";
                }
            }

            // Step 4: Handle the case where leading/trailing or consecutive spaces exist
            if (nome.startsWith(" ") || nome.endsWith(" ") || nome.contains("  ")) {
                // Check for the "Invalid Field" tooltip
                frame.locator(name).hover();
                frame.locator(msg).waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(5000)
                );

                String textContent = frame.locator(msg).textContent();
                if (textContent != null) {
                    return textContent;
                }
            }

            // If no errors or tooltips, assume the input is valid
            return actualValue;

        } catch (TimeoutError e) {
            // Step 5: If no tooltip appears but an error was expected, return a default invalid message
            return "ToolTip non viene visualizzato";
        }
    }

    public String validazioneNomeOLD(String nome) {
        try {
            frame.locator(name).type(nome);
            frame.locator(name).press("Tab");
            frame.locator(name).hover();
            frame.locator(msg).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));

            String textContent = frame.locator(msg).textContent();
            /*if (textContent != null) {
                textContent = textContent.trim().toLowerCase();
            }*/
            return textContent;
        } catch (TimeoutError e) {
            // Return this message if the tooltip is not visible within the timeout
            return "ToolTip non viene visualizzato";
        }
    }

    public String validazioneTelefono(String telefono, String expectedValue) {
        try {
            // Step 1: Type the input value into the phone number field
            frame.locator(phone).type(telefono);
            frame.locator(phone).press("Tab");

            // Step 2: Retrieve the current value in the phone number input field
            String actualValue = frame.locator(phone).inputValue().replaceAll("\\s+", "").trim();

            // Step 3: Check if the input contains alphabetic characters, special characters, or spaces
            if (telefono.matches(".*[a-zA-Z!@#$%^&*() ].*")) {
                // Step 4: If invalid characters are still present, hover and check for the tooltip
                if (!actualValue.equals(expectedValue)) {
                    frame.locator(phone).hover();
                    frame.locator(msg).waitFor(new Locator.WaitForOptions()
                            .setState(WaitForSelectorState.VISIBLE)
                            .setTimeout(5000)
                    );

                    // Capture the tooltip message if it appears
                    String textContent = frame.locator(msg).textContent();
                    if (textContent != null) {
                        return textContent;
                    }
                }
            }

            // Step 5: If the field does not contain invalid characters, return a valid result
            return actualValue;

        } catch (TimeoutError e) {
            // Step 6: If no tooltip is visible within the timeout
            return "Expected tooltip, but none appeared.";
        }
    }

    public String validazioneTelefono_OLD(String telefono) {
        try {
            frame.locator(phone).type(telefono);
            frame.locator(phone).press("Tab");
            frame.locator(phone).hover();
            frame.locator(msg).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));

            String textContent = frame.locator(msg).textContent();
            /*if (textContent != null) {
                textContent = textContent.trim().toLowerCase();
            }*/
            return textContent;
        } catch (TimeoutError e) {
            // Return this message if the tooltip is not visible within the timeout
            return "ToolTip non viene visualizzato";
        }
    }

    public String validazioneEmail(String mail, String expectedValue) {
        try {
            // Step 1: Type the input value into the email field
            frame.locator(email).type(mail);
            frame.locator(email).press("Tab");

            // Step 2: Retrieve the current value in the email input field
            String actualValue = frame.locator(email).inputValue().trim();

            // Step 3: Validate the email format
            // Check if the email contains invalid characters or incorrect format
            if (mail.matches(".*[\\s!#$%^&*()={}<>?/\\[\\]~].*") ||
                    !mail.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {

                // If invalid characters or format are detected, check for the tooltip
                if (!actualValue.equals(expectedValue)) {
                    frame.locator(email).hover();
                    frame.locator(msg).waitFor(new Locator.WaitForOptions()
                            .setState(WaitForSelectorState.VISIBLE)
                            .setTimeout(5000)
                    );

                    // Capture the tooltip message if it appears
                    String textContent = frame.locator(msg).textContent();
                    if (textContent != null) {
                        return textContent;
                    } else {
                        return "ToolTip non viene visualizzato.";
                    }
                }
            }

            // Step 4: If no invalid characters or formats are detected, return the valid result
            return actualValue;

        } catch (TimeoutError e) {
            // Step 5: If no tooltip is visible within the timeout
            return "ToolTip non viene visualizzato.";
        }
    }


    public String validazioneEmail_OLD(String mail) {
        try {
            frame.locator(email).type(mail);
            frame.locator(email).press("Tab");
            frame.locator(email).hover();
            frame.locator(msg).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));

            String textContent = frame.locator(msg).textContent();
            /*if (textContent != null) {
                textContent = textContent.trim().toLowerCase();
            }*/
            return textContent;
        } catch (TimeoutError e) {
            // Return this message if the tooltip is not visible within the timeout
            return "ToolTip non viene visualizzato";
        }
    }


    public String validazioneCodiceFiscaleIncolla(String codiceFiscale) {
        try {
            // Copy the codiceFiscale to the clipboard
            page.evaluate("codiceFiscale => navigator.clipboard.writeText(codiceFiscale)", codiceFiscale);

            // Focus on the taxCode locator
            frame.locator(taxCode).focus();

            // Paste the codiceFiscale from the clipboard
            frame.locator(taxCode).press("Control+V");
            // Press Tab to move focus
            frame.locator(taxCode).press("Tab");

            // Hover over the taxCode locator to trigger the tooltip
            frame.locator(taxCode).focus();
            frame.locator(taxCode).hover();

            // Wait for the tooltip message to be visible
            frame.locator(msg).waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(5000));

            // Get the text content of the tooltip message
            String textContent = frame.locator(msg).textContent();
        /*if (textContent != null) {
            textContent = textContent.trim().toLowerCase();
        }*/
            return textContent;
        } catch (TimeoutError e) {
            // Return this message if the tooltip is not visible within the timeout
            return "ToolTip non viene visualizzato";
        }
    }


    public String validazioneCognomeIncolla(String cognome) {
        try {
            // Copy the codiceFiscale to the clipboard
            page.evaluate("cognome => navigator.clipboard.writeText(cognome)", cognome);

            // Focus on the taxCode locator
            frame.locator(lastname).focus();

            // Paste the codiceFiscale from the clipboard
            frame.locator(lastname).press("Control+V");

            // Press Tab to move focus
            frame.locator(lastname).press("Tab");

            // Hover over the taxCode locator to trigger the tooltip
            frame.locator(lastname).hover();

            // Wait for the tooltip message to be visible
            frame.locator(msg).waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(5000));

            // Get the text content of the tooltip message
            String textContent = frame.locator(msg).textContent();
        /*if (textContent != null) {
            textContent = textContent.trim().toLowerCase();
        }*/
            return textContent;
        } catch (TimeoutError e) {
            // Return this message if the tooltip is not visible within the timeout
            return "ToolTip non viene visualizzato";
        }
    }


    public String validazioneNomeIncolla(String nome) {
        try {
            // Copy the codiceFiscale to the clipboard
            page.evaluate("nome => navigator.clipboard.writeText(nome)", nome);

            // Focus on the taxCode locator
            frame.locator(name).focus();

            // Paste the codiceFiscale from the clipboard
            frame.locator(name).press("Control+V");

            // Press Tab to move focus
            frame.locator(name).press("Tab");

            // Hover over the taxCode locator to trigger the tooltip
            frame.locator(name).hover();

            // Wait for the tooltip message to be visible
            frame.locator(msg).waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(5000));

            // Get the text content of the tooltip message
            String textContent = frame.locator(msg).textContent();
        /*if (textContent != null) {
            textContent = textContent.trim().toLowerCase();
        }*/
            return textContent;
        } catch (TimeoutError e) {
            // Return this message if the tooltip is not visible within the timeout
            return "ToolTip non viene visualizzato";
        }
    }


    public String validazioneTelefonoIncolla(String telefono) {
        try {
            // Copy the codiceFiscale to the clipboard
            page.evaluate("telefono => navigator.clipboard.writeText(telefono)", telefono);

            // Focus on the taxCode locator
            frame.locator(phone).focus();

            // Paste the codiceFiscale from the clipboard
            frame.locator(phone).press("Control+V");

            // Press Tab to move focus
            frame.locator(phone).press("Tab");

            // Hover over the taxCode locator to trigger the tooltip
            frame.locator(phone).hover();

            // Wait for the tooltip message to be visible
            frame.locator(msg).waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(5000));

            // Get the text content of the tooltip message
            String textContent = frame.locator(msg).textContent();
        /*if (textContent != null) {
            textContent = textContent.trim().toLowerCase();
        }*/
            return textContent;
        } catch (TimeoutError e) {
            // Return this message if the tooltip is not visible within the timeout
            return "ToolTip non viene visualizzato";
        }
    }


    public String validazioneEmailIncolla(String mail) {
        try {
            // Copy the codiceFiscale to the clipboard
            page.evaluate("mail => navigator.clipboard.writeText(mail)", mail);
            frame.locator(email).focus();
            frame.locator(email).press("Control+V");
            frame.locator(email).press("Tab");
            frame.locator(email).hover();

            // Wait for the tooltip message to be visible
            frame.locator(msg).waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(5000));

            // Get the text content of the tooltip message
            String textContent = frame.locator(msg).textContent();
        /*if (textContent != null) {
            textContent = textContent.trim().toLowerCase();
        }*/
            return textContent;
        } catch (TimeoutError e) {
            // Return this message if the tooltip is not visible within the timeout
            return "ToolTip non viene visualizzato";
        }
    }

    public String verificaDatiPrincipali(String codiceFiscale, String ngsesso) {
        try {
            // Type the codiceFiscale and press Tab
            frame.locator(taxCode).type(codiceFiscale);
            frame.locator(taxCode).press("Tab");

            // Wait for the element to be visible
            frame.locator(naturaGiuridicaSesso).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));

            // Get the text content of the element
            String textContent = frame.locator(naturaGiuridicaSesso).textContent();

            if (textContent == null) {
                return null;
            }

            // Find the starting index of ngsesso in textContent
            int startIndex = textContent.indexOf(ngsesso);
            if (startIndex != -1) {
                // Return the substring that matches ngsesso
                return textContent.substring(startIndex, startIndex + ngsesso.length());
            }

            return null;
        } catch (TimeoutError e) {
            // Return this message if the tooltip is not visible within the timeout
            return "ToolTip non viene visualizzato";
        }
    }

    public String verificaPresenzaMsgTuttiCampi() {
        try{
            frame.locator(msgCompletaCampi).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
            String textContent = frame.locator(msgCompletaCampi).textContent();

            return textContent;
        } catch (TimeoutError e) {
            // Return this message if the tooltip is not visible within the timeout
            return "ToolTip non viene visualizzato";
        }
    }

    public String verificaDatiPrincipaliIncolla(String codiceFiscale, String ngsesso) {
        try {
            page.evaluate("codiceFiscale => navigator.clipboard.writeText(codiceFiscale)", codiceFiscale);
            frame.locator(taxCode).focus();
            frame.locator(taxCode).press("Control+V");
            frame.locator(taxCode).press("Tab");

            // Wait for the element to be visible
            frame.locator(naturaGiuridicaSesso).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));

            // Get the text content of the element
            String textContent = frame.locator(naturaGiuridicaSesso).textContent();

            if (textContent == null) {
                return null;
            }

            // Find the starting index of ngsesso in textContent
            int startIndex = textContent.indexOf(ngsesso);
            if (startIndex != -1) {
                // Return the substring that matches ngsesso
                return textContent.substring(startIndex, startIndex + ngsesso.length());
            }

            return null;
        } catch (TimeoutError e) {
            // Return this message if the tooltip is not visible within the timeout
            return "ToolTip non viene visualizzato";
        }
    }

    public String verificaToolTipObbligatorio(String locator) {
        try {

            // Special handling for email field: Expect no tooltip
            if (locator.equals("input[formcontrolname='email']")) {
                return "Tooltip should not be visible for email";
            }

            frame.locator(locator).focus();
            frame.locator(locator).press("Tab");
            frame.locator(locator).hover();

            frame.locator(msg).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));

            String textContent = frame.locator(msg).textContent();

            return textContent;
        } catch (TimeoutError e) {
            // If the tooltip is not visible within the timeout
            if (locator.equals("input[formcontrolname='email']")) {
                return "No tooltip visible as expected for email";
            } else {
                return "ToolTip non viene visualizzato";
            }
        }
    }

    public boolean validazioneCognomeValido(String cognome) {
        try {
            // Enter the surname and press Tab
            frame.locator(lastname).type(cognome);
            frame.locator(lastname).press("Tab");

            // Wait for the element to be attached
            frame.locator(invalidClass).or(frame.locator(invalidClass2)).waitFor(
                    new Locator.WaitForOptions()
                            .setState(WaitForSelectorState.ATTACHED)
                            .setTimeout(500)
            );

            // If the waitFor does not throw an exception, the element was found
            return false;
        } catch (TimeoutError e) {
            // If a TimeoutError is caught, the element was not found within the timeout
            return true;
        }
    }


    public boolean validazioneNomeValido(String nome) {
        try {
            // Enter the name and press Tab
            frame.locator(name).type(nome);
            frame.locator(name).press("Tab");
            frame.locator(name).hover();

            // Wait for the tooltip to be visible
            frame.locator(msg).waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(500)  // Updated timeout value
            );

            // If no exception is thrown, the tooltip is visible
            return false;
        } catch (TimeoutError e) {
            // Tooltip is not visible within the timeout
            return true;
        }
    }

    public boolean validazioneTelefonoValido(String telefono) {
        try {
            // Enter the phone number and press Tab
            frame.locator(phone).type(telefono);
            frame.locator(phone).press("Tab");
            frame.locator(phone).hover();

            // Wait for the tooltip to be visible
            frame.locator(msg).waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(500)  // Updated timeout value
            );

            // If no exception is thrown, the tooltip is visible
            return false;
        } catch (TimeoutError e) {
            // Tooltip is not visible within the timeout
            return true;
        }
    }

    public boolean validazioneEmailValido(String mail) {
        try {
            // Enter the email and press Tab
            frame.locator(email).type(mail);
            frame.locator(email).press("Tab");
            frame.locator(email).hover();

            // Wait for the tooltip to be visible
            frame.locator(msg).waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(500)  // Updated timeout value
            );

            // If no exception is thrown, the tooltip is visible
            return false;
        } catch (TimeoutError e) {
            // Tooltip is not visible within the timeout
            return true;
        }
    }



    public boolean validazioneCognomeValidoIncolla(String cognome) {
        try {
            // Write the surname to the clipboard
            page.evaluate("cognome => navigator.clipboard.writeText(cognome)", cognome);

            // Focus on the lastname locator
            frame.locator(lastname).focus();
            // Paste the surname from the clipboard
            frame.locator(lastname).press("Control+V");

            // Press Tab to move out of the field
            frame.locator(lastname).press("Tab");

            // Wait for the element to be attached
            frame.locator(invalidClass).or(frame.locator(invalidClass2)).waitFor(
                    new Locator.WaitForOptions()
                            .setState(WaitForSelectorState.ATTACHED)
                            .setTimeout(500)  // Updated timeout value
            );

            // If the waitFor does not throw an exception, the element was found
            return false;
        } catch (TimeoutError e) {
            // If a TimeoutError is caught, the element was not found within the timeout
            return true;
        }
    }

    public boolean validazioneNomeValidoIncolla(String nome) {
        try {
            // Write the name to the clipboard
            page.evaluate("nome => navigator.clipboard.writeText(nome)", nome);

            // Focus on the name locator
            frame.locator(name).focus();
            // Paste the name from the clipboard
            frame.locator(name).press("Control+V");

            // Press Tab to move out of the field
            frame.locator(name).press("Tab");

            // Hover to trigger any tooltip if applicable
            frame.locator(name).hover();

            // Wait for the tooltip to be visible
            frame.locator(msg).waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(500)  // Updated timeout value
            );

            // If no exception is thrown, the tooltip is visible
            return false;
        } catch (TimeoutError e) {
            // Tooltip is not visible within the timeout
            return true;
        }
    }

    public boolean validazioneTelefonoValidoIncolla(String telefono) {
        try {
            // Write the phone number to the clipboard
            page.evaluate("telefono => navigator.clipboard.writeText(telefono)", telefono);

            // Focus on the phone locator
            frame.locator(phone).focus();
            // Paste the phone number from the clipboard
            frame.locator(phone).press("Control+V");

            // Press Tab to move out of the field
            frame.locator(phone).press("Tab");

            // Hover to trigger any tooltip if applicable
            frame.locator(phone).hover();

            // Wait for the tooltip to be visible
            frame.locator(msg).waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(500)  // Updated timeout value
            );

            // If no exception is thrown, the tooltip is visible
            return false;
        } catch (TimeoutError e) {
            // Tooltip is not visible within the timeout
            return true;
        }
    }

    public boolean validazioneEmailValidoIncolla(String mail) {
        try {
            // Write the email to the clipboard
            page.evaluate("mail => navigator.clipboard.writeText(mail)", mail);

            // Focus on the email locator
            frame.locator(email).focus();
            // Paste the email from the clipboard
            frame.locator(email).press("Control+V");

            // Press Tab to move out of the field
            frame.locator(email).press("Tab");

            // Hover to trigger any tooltip if applicable
            frame.locator(email).hover();

            // Wait for the tooltip to be visible
            frame.locator(msg).waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(500)  // Updated timeout value
            );

            // If no exception is thrown, the tooltip is visible
            return false;
        } catch (TimeoutError e) {
            // Tooltip is not visible within the timeout
            return true;
        }
    }




    public void inserimentoPF(String codicefiscale, String lastName, String nome, String telefono, String mail){
        frame.locator(taxCode).type(codicefiscale);
        frame.locator(lastname).type(lastName);
        frame.locator(name).type(nome);
        frame.locator(phone).type(telefono);
        frame.locator(email).type(mail);
    }

    public boolean verificaPresenzaListe() {
        frame.locator(liste).first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(10000));
        return frame.locator(liste).first().isVisible();
    }

    public boolean verificaPresenzaEventi() {
        frame.locator(eventi).first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(15000));
        return frame.locator(eventi).first().isVisible();
    }

    public boolean verificaPresenzaPFPG(String tipoPersona){
        return frame.getByRole(AriaRole.RADIO, new FrameLocator.GetByRoleOptions().setName(tipoPersona)).isVisible();
    }

    public boolean verificatastoAnnulla(){
        return frame.locator(annulla).isVisible();
    }

    public boolean verificatastoProsegui(){
        return frame.locator(prosegui).isVisible();
    }

    public boolean verificaWarningCF(){
        //return page.locator(invalidClass).getByLabel("warning_filledCodice Fiscale").isVisible();
        return frame.getByLabel("warning_filledCodice Fiscale").isVisible();
    }

    public boolean verificaWarningCognome(){
        //return page.locator(invalidClassCognome).getByLabel("warning_filledCognome").isVisible();
        return frame.getByLabel("warning_filledCognome").isVisible();
    }

    public void verificatastoExit() {
        try {
            if (frame.locator(exit).isVisible()) {
                frame.locator(exit).click();
            } else {
                System.out.println("Exit element is not visible.");
            }
        } catch (Exception e) {
            System.out.println("Element not found or error occurred: " + e.getMessage());
        }
    }

}