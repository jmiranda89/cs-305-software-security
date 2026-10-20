# CS-305 Project One — Artemis Financial Security Assessment

## Overview

For Project One, I performed a security assessment of the Artemis Financial application. The goal was to identify security vulnerabilities in the existing Java application and recommend changes that would improve its security.

## What I Did

I reviewed the application's source code, configuration, and dependencies using the Vulnerability Assessment Process Flow Diagram provided by SNHU.

I focused on identifying security issues through manual code review and automated dependency scanning using OWASP Dependency-Check.

## Key Findings

During the manual review, I identified 10 security findings, including:

- Hard-coded database credentials and excessive database permissions
- Missing authentication and authorization on REST endpoints
- Missing application HTTPS configuration and no explicit JDBC transport encryption requirement
- Missing input validation
- Unsafe error handling
- Weak encapsulation and financial input validation
- Large multipart upload limits

OWASP Dependency-Check 12.2.2 scanned 38 dependencies (20 unique) and identified 252 vulnerability findings across 11 vulnerable dependencies, with no findings suppressed. These historical scanner findings describe outdated third-party libraries; they are not 252 confirmed exploitable vulnerabilities in this application.

The report does not confirm SQL injection because the reviewed method does not execute a query using its input parameters. Transport findings also depend on whether HTTPS or database TLS is enforced outside the supplied application configuration.

## Recommendations

I recommended removing hard-coded credentials, implementing HTTPS, securing the application's APIs, improving input validation, and upgrading outdated dependencies.

I also recommended integrating automated security testing into the development process to detect new vulnerabilities as they are discovered.

## What I Learned

This project helped me understand that software security requires more than just reviewing the application's code. Third-party libraries, application configurations, and how the software handles user input can all introduce security risks.

The assessment focused on identifying vulnerabilities and developing a mitigation plan rather than implementing the fixes.

## Project Artifact

Read the [completed vulnerability assessment report](CS%20305%20Project%20One%20Jonathan%20Miranda.pdf). It includes the manual findings, scan summary, and prioritized mitigation plan. The original application and full scan HTML are not included here.

[Back to the portfolio](../README.md) · [Project Two](../project-two/README.md)
