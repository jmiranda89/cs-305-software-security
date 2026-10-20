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

## What I learned

The manual assessment helped me connect individual coding decisions to security risks. Credentials in source code, excessive permissions, missing validation, and unsafe error handling all need attention alongside vulnerable dependencies.

Working on the implementation project really opened my eyes to how hashing and HTTPS do different jobs. For example, I used SHA-256 to generate a checksum and verify that the data was the same, and TLS to encrypt communications so they can't be easily read by third parties. One important thing I learned was to double-check results on my own, instead of just assuming that if the code compiles without errors, the security feature must be working right.

So, when it comes to security, it's not a one-time thing — it's a continuous process. Just because you've got a working endpoint and a successful build, it doesn't mean your application is good to go for production. There's still a lot of work to be done, like reviewing findings, figuring out what needs to be fixed first, making those fixes, and then testing everything again.

## Limitations and remaining risks

This is an **educational demonstration and is not production-ready**. The original Spring Boot baseline and unresolved dependency findings are retained to keep the coursework reproducible. No broad dependency upgrade or vulnerability suppression was performed for this portfolio.

The scan totals describe the historical reports, not a current scan or a count of verified exploits. Configuration and reachable code affect whether a finding applies. Project One explicitly does not confirm SQL injection. The PDFs contain scan summaries and screenshots; the original full Dependency-Check HTML reports and Project One application baseline are not included.

Project Two uses fixed demonstration data and does not implement authentication, authorization, or financial transaction processing. A bare SHA-256 checksum does not prove the sender's identity. AES-256-GCM is discussed as a recommendation in the report; the application does not implement AES encryption, and the report did not verify the negotiated TLS cipher suite.

Self-signed certificates are for local testing. Production use would require trusted certificates, managed secrets, dependency remediation, broader testing, and deployment hardening. Generate a new local keystore using the setup instructions; no private key, keystore, or real credential is supplied in this repository.
