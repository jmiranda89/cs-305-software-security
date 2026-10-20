# CS-305 Software Security

This repository contains my Project One and Project Two work for SNHU's CS-305 Software Security course. Both projects use the Artemis Financial scenario: a financial services company that needs to improve the security of its software and protect information sent through its application.

Project One focuses on assessing an existing application and prioritizing changes. Project Two demonstrates SHA-256 checksum verification and HTTPS in a Spring Boot application. The original reports are preserved alongside the sanitized Project Two source.

## Projects

| Project | Work completed | Artifacts |
| --- | --- | --- |
| Project One: Vulnerability assessment | Manual code and configuration review, dependency analysis, and a prioritized mitigation plan | [Project overview](project-one/README.md) · [Assessment report](project-one/CS%20305%20Project%20One%20Jonathan%20Miranda.pdf) |
| Project Two: Secure software | A `/hash` endpoint, HTTPS on port 8443, certificate generation, and functional verification | [Project overview and local setup](project-two/README.md) · [Secure software report](project-two/CS-305-Project-Two-Secure-Software-Report.pdf) · [Java source](project-two/application/src/main/java/com/snhu/sslserver/SslServerApplication.java) |

Project One documents **10 manual security findings** and **252 dependency vulnerability findings across 11 vulnerable dependencies**. Its mitigation plan is a set of recommendations; the report does not establish that those fixes were implemented.

Project Two adds and tests checksum generation and encrypted transport. Its historical dependency scan still reports **254 findings across 14 vulnerable dependencies**. Adding HTTPS did not resolve the older libraries' vulnerabilities.

## Security concepts demonstrated

- Manual vulnerability assessment of code, configuration, input handling, access controls, and error handling.
- Dependency vulnerability assessment using OWASP Dependency-Check, including interpretation of scanner limitations.
- SHA-256 checksum generation with Java `MessageDigest` and explicit UTF-8 encoding.
- Independent checksum verification using PowerShell.
- HTTPS/TLS configuration, self-signed RSA certificates, Java Keytool, and PKCS12 keystores.
- Secure configuration using environment variables and keeping credentials and private keys out of source control.
- Functional testing of endpoint responses and encrypted communication.
- Cryptographic best practices, including the distinction between hashing, encryption, and authentication.

## Tools and technologies

Java, Spring Boot 2.2.4.RELEASE, Maven, JUnit 5, Spring MockMvc, OWASP Dependency-Check, Java Keytool, PowerShell, and IntelliJ IDEA. Project One used Dependency-Check 12.2.2; Project Two uses 13.0.0. Local build and run instructions are in the [Project Two README](project-two/README.md).

## CI/CD

The GitHub Actions workflows add build checks and security analysis. They do not deploy the application.

| Workflow | Runs | Purpose |
| --- | --- | --- |
| [Java CI](.github/workflows/java-ci.yml) | PRs to `main`, pushes to `main`, and manual runs | Builds Project Two with Temurin Java 11 and Maven `clean package`; runs the context and `/hash` tests and uploads test results |
| [CodeQL](.github/workflows/codeql.yml) | PRs to `main`, pushes to `main`, Tuesdays at 06:23 UTC, and manual runs | Analyzes the compiled Java application and publishes findings to code scanning after advanced setup is confirmed |
| [Dependency Review](.github/workflows/dependency-review.yml) | PRs to `main` | Fails when dependency changes introduce known high or critical vulnerabilities, including development and unknown scopes |
| [OWASP Dependency-Check](.github/workflows/dependency-check.yml) | Wednesdays at 07:41 UTC and manual runs from `main` | Assesses the existing dependency baseline and uploads an HTML report |

Ordinary Java CI needs no NVD API key, certificate, or keystore password. These are MockMvc tests; they do not test HTTPS/TLS. Passing CI does not mean the application is free of security vulnerabilities. The historical Project Two baseline remains **254 findings across 14 vulnerable dependencies**.

Dependency Review checks what a PR introduces. Dependency-Check assesses the existing application dependencies and needs the `NVD_API_KEY` repository secret. Configure that secret with a new or rotated key; do not reuse a key from an old assignment archive. A missing secret fails the scan workflow without claiming an assessment ran. A completed scan can still report vulnerabilities.

The recommended required PR checks are **Build and Test** and **Review Dependency Changes**. Add **Analyze Java** after CodeQL setup and a hosted run are verified. Failed checks block merging only when branch protection or a ruleset requires them. Java CI and Dependency Review have no path filters, so documentation-only PRs still receive checks. The scheduled baseline scan is separate from PR validation.

See [Project Two CI/CD setup and limitations](project-two/README.md#cicd) for CodeQL configuration, NVD secret setup, manual scans, and result locations. Repository security settings and hosted workflow results were not verified with the read-only implementation account.

## What I Learned

Connecting individual code decisions with potential security risks helped me a great deal during the manual assessment. Aside from the vulnerable dependencies, credentials in the source code, excessive permissions, missing validation, and unsafe error handling also have to be addressed.

I tried to combine manual code checking with dependency scanning and to verify findings against the application's configuration. Secure coding at Artemis Financial will help protect customers' personal and financial information, help keep services available to customers, and maintain customers' trust and the company's good name.

Working with the implementation of hashing and HTTPS helped me understand how they perform different jobs. I used SHA-256 as a hashing function to produce a checksum of a fixed test string, which I verified independently using PowerShell to confirm that both results matched. I also used HTTPS over port 8443 to encrypt communications and prevent third parties from easily reading the information while it was being sent over the web. It was also important to verify results independently rather than just blindly trusting that as long as the code compiled without any errors, all must be well with the implementation of the desired security function.

Security features such as HTTPS (self-signed certificate on port 8443), SHA-256 checksum generation and verification, and storing the keystore password in an environment variable have been implemented to enhance security. In future assessments of this project, security could be tested by performing a manual review of the code and using OWASP Dependency-Check to test for vulnerable libraries. The fixes for security issues should be prioritized based on factors such as severity, exploitability, runtime configuration, and business impact.

To verify my refactored code, I ran the automated tests for the application context as well as the /hash response, verified that the HTTPS service was listening on port 8443 and responding with HTTP 200, and verified the Java-generated checksum against the independently calculated PowerShell checksum. Furthermore, I performed a manual code and configuration review, as well as OWASP Dependency-Check after refactoring the code. These checks do not guarantee that no new vulnerabilities were introduced. The historical scan still reports several unresolved findings, and a fresh scan attempted during the portfolio validation did not complete.

For future projects, I would like to continue using Java's MessageDigest class with explicit UTF-8 encoding of the input data. Keytool from the Java SDK can be used to create local test certificates. Maven can be used for builds and dependency scanning. Additionally, I would like to continue using independent checksums, storing passwords in environment variables, and keeping private keys out of Git.

For future employers, I could show my Project One assessment of the existing Artemis Financial application, where I identified possible security problems and provided mitigation recommendations in the assessment report. In Project Two, I added security features to the Artemis Financial application, implemented and tested them, and documented what still needs to be done before the application is ready for production use. These projects demonstrate my ability to identify security risks, implement security improvements, and verify that the changes work as expected.

So there's a lot more to be done in terms of security, such as checking out findings, prioritizing them, fixing them, and retesting the whole application afterwards. Security is not a one-time activity, and just because an application works does not mean it is completely secure.

## Limitations and remaining risks

This is an **educational demonstration and is not production-ready**. The original Spring Boot baseline and unresolved dependency findings are retained to keep the coursework reproducible. No broad dependency upgrade or vulnerability suppression was performed for this portfolio.

The scan totals describe the historical reports, not a current scan or a count of verified exploits. Configuration and reachable code affect whether a finding applies. Project One explicitly does not confirm SQL injection. The PDFs contain scan summaries and screenshots; the original full Dependency-Check HTML reports and Project One application baseline are not included.

Project Two uses fixed demonstration data and does not implement authentication, authorization, or financial transaction processing. A bare SHA-256 checksum does not prove the sender's identity. AES-256-GCM is discussed as a recommendation in the report; the application does not implement AES encryption, and the report did not verify the negotiated TLS cipher suite.

Self-signed certificates are for local testing. Production use would require trusted certificates, managed secrets, dependency remediation, broader testing, and deployment hardening. Generate a new local keystore using the setup instructions; no private key, keystore, or real credential is supplied in this repository.
