# Assignment 2 - The Anatomy of a Hypermedia Search Engine

Course: Distributed Systems

**Deadline: October 15, 2026, at 23:59 CEST.**

One partner creates the private template
repository and gives the other **Write** access. Record both students here.

| student name | GitHub handle |
|---|---|
| `TODO` | `TODO` |
| `TODO` | `TODO` |

>In this assignment, you will get practical experience with designing and implementing hypermedia systems. Given an artificial hypermedia environment you need to run on your machine, you will create (design and implement) a basic search engine for the given environment---including a crawler, a multithreaded version of the crawler, an API specification and its implementation.

**The assignment repository:**
**<https://github.com/HSG-DS-HS26/BCS-DS-Assignment-2>**

One group member should create a repo by clicking **Use this template** to create the group's private repository. Do not fork the repository or
clone it directly! The template features allow us to have your repository private instead of public. See [SETUP.md](SETUP.md) for a more detailed walkthrough.


| | | |
|---|---|---|
| **Before you start** | your own private copy of this repository | **0** |
| **Task 1** | Go Crawl!| **1.5** |
| **Task 2** | Go Crawl but Mind the Network! | **1.5** |
| **Task 3** | API Design | **2.5** |
| **Task 4** | API Implementation | **1.5** |
| **Task 5** | defend your submission | **1** |
| | **hand in** | |
| **Task 6** | a live task | **2** |

---

## Before you start (0 pt)

The repository you are reading is the original. Make your own **private** copy
and give the examiner **read** access:

**[SETUP.md](SETUP.md)**

Write your name and GitHub project URL in the [`Report.md`](Report.md) file. At the end of the project, submit this file on Canvas.

Clone the hypermedia environment project [`here`](https://github.com/HSG-DS-HS26/hypermedia-environment).

Run the hypermedia environment with:
```bash
./run_all_servers.sh
```
or on Windows, with Powershell:

```bash
.\run_all_servers.ps1
```
---

## ★ Task 1: Go Crawl! (1.5 pt)

In this task, you will first write a [`simple crawler`](src/main/java/searchengine/SimpleCrawler.java) that navigates the entire hypermedia environment to build an index of all encountered Web pages. The crawler will start from the following entry point (seed URL): http://localhost:8080/hypermedia-environment/5f018e9111504118 (update the port if needed).
Once the crawling is complete, your crawler should return a table that contains, for each Web page, an optional permanent status, then the 3 optional URLs recorded for permanent pages, the contents (three terms) that occur on that Web page. If more URLs are present on the page (which is not the case in the provided hypermedia environment), only the first three links are stored in the table. This table, which we refer to as an index, should be stored in a CSV file [`index.csv`](src/main/resources/index.csv) with each line corresponding to a crawled page. See example below:

```
"http://localhost:8080/hypermedia-environment/5d525105043c426e","permanent","http://localhost:8080/hypermedia-environment/78285e61226e48ad","http://localhost:8080/hypermedia-environment/51546176a65a4e25","http://localhost:8080/hypermedia-environment/0914b354d5f7441b","truth","despair","incredible"
"http://127.0.0.1:8081/hypermedia-environment/3c7052e3d2e64c8d","","","","","misery","port","anthology"
"http://localhost:8080/hypermedia-environment/222cbb1e14684ae4","","","","","vaccine","despair","port"
. . .
```



### To do

- [ ] Implement a crawler that creates an index following the required format, including the addition of "permanent" and seen URLs when a Web page is cacheable and is unchangeable (e.g., its Cache-Control header includes immutable). If this page is already present in the stored index with a permanent label, then the crawler should not crawl it again but it should reuse the stored URLs to continue crawling.
- [ ] Test your crawler with `./gradlew simple_crawl -Purl="http://localhost:8080/hypermedia-environment/5f018e9111504118"` (On Windows: `.\gradlew.bat simple_crawl -Purl="http://localhost:8080/hypermedia-environment/5f018e9111504118"`). Record in [`Report.md`](Report.md) the time taken to run the crawler when the index file is empty.
- [ ] Run the servers 1 to 5 individually (see the instructions in the [`README.md`](https://github.com/HSG-DS-HS26/hypermedia-environment/blob/main/README.md) of the hypermedia-environment project). Some servers have delays when performing the requests. Using the simple crawler, can you identify which servers have delays and estimate the corresponding delays? Explain in [`Report.md`](Report.md).

### Inverted Index and Searcher

We provide you with two components: the index inverter and the searcher. You can run them in order: first, index inverter, then searcher once you completed the search engine. It would help you test that your crawler works properly.

The [`index inverter`](src/main/java/searchengine/IndexInverter.java) creates an inverted index ([`inverted_index.csv`](src/main/resources/inverted_index.csv)) from the index.

Use `./gradlew invertIndex` (On Windows: `.\gradlew.bat invertIndex`) to invert the index.


The searcher uses this inverted index to answer a user query.

Use `./gradlew search -Pkeyword=over` (On Windows: `.\gradlew.bat search -Pkeyword=over`) to perform a search.

Here is a  partial example of an inverted index:

```
"inflation","http://127.0.0.1:8081/hypermedia-environment/00f0daf5cda94ffa","http://127.0.0.1:8081/hypermedia-environment/01bc62ff8b4c4012", ...
"insect","http://127.0.0.1:8081/hypermedia-environment/002479eeac1f4372","http://127.0.0.1:8081/hypermedia-environment/01612ff4af274539",...
. . .
```

### Grading

The simple crawler is tested on its capability to create the required index and its handling of caching. You must also answer the associated questions in [`Report.md`](Report.md).

---


## ★ Task 2: Go Crawl but Mind the Network! (1.5 pt)

The simple crawler spends a lot of time waiting for responses from the Web servers. This is because the Web server takes time to process the client's request and, on the Internet, there is a communication delay. Multi-threading enables running different threads in parallel to wait for different server responses. Therefore, we propose using multi-threading to enable a performance increase.

Complete the [`multithreaded crawler`](src/main/java/searchengine/MultithreadedCrawler.java).

The multithreaded crawler should produce the same index as the simple crawler, while also reducing significantly the time required for crawling.

Experiment with different variations on the number of threads. What is the number N of CPU cores of your machine? What is the gain in crawling time when using N/2 threads? When using N threads? The gain is measured by time taken by single crawler divided by time taken by multithreaded crawler.

### To do

- [ ] Implement [`multithreaded crawler`](src/main/java/searchengine/MultithreadedCrawler.java) to provide the same features as the simple crawler but with multithreading.
- [ ] Test your crawler with `./gradlew multithreaded_crawl -Purl="http://localhost:8080/hypermedia-environment/5f018e9111504118" -PmaxThreads=4` (On Windows: `.\gradlew.bat multithreaded_crawl -Purl="http://localhost:8080/hypermedia-environment/5f018e9111504118" -PmaxThreads=4`)
- [ ] Experiment with different variations on the number of threads. What is the number N of CPU cores of your machine? What is the gain in crawling time when using N/2 threads? When using N threads? Indicate the results in [`Report.md`](Report.md).

### Grading

The multithreaded crawler is evaluated by its ability to implement the same features as the simple crawler, while demonstrating significantly lower processing with multithreading. The question in [`Report.md`](Report.md) must be answered.

---


## ★ Task 3: API Design (2.5 pt)

You should implement an OpenAPI specification for your search engine. To do so, complete the file [`api_specification.yaml`](src/main/resources/api_specification.yaml).

Some of the operations that you need to implement require defining and using administration (admin) endpoints. Each one of these operations requires the user to be authorized (i.e., provide a valid API key). This requires using the Authorization header with a valid API key. An API key is a secret key that identifies that a user to use a resource. For testing purposes, you should use the hardcoded mytoken123 as a valid API key (e.g., "Authorization: Bearer mytoken123" is valid authentication). Non-admin operations are available for all users.

You should implement the following features:
- a search operation that allows users to get the URLs of the pages containing a certain keyword;
- (admin) launching a new crawling operation from a seed URL given as input and a crawler to use;
- (admin) retrieving the status of the crawler, e.g., whether it is free to use;
- (admin) retrieving the status of the inverted index, e.g., whether it needs to be regenerated after crawling;
- (admin) regenerating the inverted index;
- (admin) deleting a URL from the index;
- (admin) updating (or adding) the keywords associated with a given URL in the index. The new page is then considered to be non-permanent.

Use [`Swagger Editor`](https://editor.swagger.io/) to write and validate your OpenAPI specification.

Write down your design choices for the API specification in [`Report.md`](Report.md).

> You can learn more about how to write an API specification using the documentation from Mozilla. In particular, read the documentation concerning [`HTTP methods`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods), and [ `status codes`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status).

### To do

- [ ] Complete [`api_specification.yaml`](src/main/resources/api_specification.yaml).
- [ ] Validate your specification with [`Swagger Editor`](https://editor.swagger.io/).
- [ ] Write down your design choices for the API specification in [`Report.md`](Report.md).

### Grading

The specification is correct and contains all the expected features (including the use of mytoken123 as required API key). It follows the good practices of API design and the design choices are properly justified.

---

## ★ Task 4: API Implementation (1.5 pt)

Complete [`SearchEngineServer`](src/main/java/searchengine/SearchEngineServer.java) to implement a search engine server following the defined API specification. Your implementation should use the provided [`searcher`](src/main/java/searchengine/Searcher.java) and [`index inverter`](src/main/java/searchengine/IndexInverter.java). Your implementation should use the framework [`Javalin`](https://javalin.io/).

### To do

- [ ] Implement the server in [`SearchEngineServer.java`](src/main/java/searchengine/SearchEngineServer.java) following the API specification that you define. Use the framework [`Javalin`](https://javalin.io/).
- [ ] Run your HTTP server with `./gradlew run` (on Windows: `.\gradlew.bat run`). The base URL will be: http://localhost:9000/. If needed, change the port in [`SearchEngineApplication.java`](src/main/java/searchengine/SearchEngineApplication.java).
- [ ] Write down in the [`Report.md`](Report.md) the queries that you performed on your server and what you obtained. Record each HTTP request (method, URL, headers, body) and response (status code, headers, body). Associate each query and corresponding response with the corresponding element of the OpenAPI specification and explain whether the results match the OpenAPI specification.


### Grading

The server works properly and follows the API specification. The corresponding question in the [`Report.md`](Report.md) is answered.


---

## ★ Task 5: defend your submission (1pt, and required)

> **EXERCISE**: the viva itself, against [`viva.yml`](viva.yml). Required.

Your submission is not complete without it.

When all tasks from 1 to 4 are completed, **commit and push**.

Open [the viva site](https://wiser-sp4.interactions.ics.unisg.ch) and sign in with
GitHub. Select your repository and the commit you pushed, then answer the viva's
questions about your code.

When the viva ends, **it commits its record to your repository and pushes it
itself**. That commit is on GitHub and not yet in your Codespace or on your
laptop, so **pull** before you do anything else. If you skip the pull, your next
push is rejected, because GitHub has a commit you do not.

Archive submissions and students who withhold GitHub access from the automated
AI viva complete the viva directly with the TA on Teams. For this you need
to inform the TA beforehand.

**[.viva/README.md](.viva/README.md)** explains how to run the viva and what to
commit afterwards.

**You get points for:** participating in the Viva and including the results within your project.

### To do

- [ ] Tasks 1-4 are committed and pushed
- [ ] Open [the viva site](https://wiser-sp4.interactions.ics.unisg.ch), select
  your repository and commit, and answer its questions
- [ ] **Pull**, and check that `.viva/` is now in your repository. In VS Code:
  **Source Control** -> **Sync Changes**, or `git pull` in a terminal
- [ ] Attend the agreed direct Teams viva if you use an archive submission or
  withhold GitHub access from the automated AI viva

### Grading


The actual content of the viva is not yet graded, but you need to have the viva
logs in the repository to get the point here.

## ★ Task 6: a live task (2 pt)

> **Live Task**: In the exercise session after submission. Mandatory attendance.

In the session after the submission deadline of the assignment, you will be given a live task to complete.

### To do (during the exercise session after the submission deadline of the assignment)

- [ ] Pull the "live task" commit describing the live task in the project template. Follow the git instructions provided in [`SETUP.md`](SETUP.md).
- [ ] In this new commit, you will find a file [`LIVE_TASK.md`](LIVE_TASK.md). Follow the provided instructions.
- [ ] Update your project, and make a new **commit and push**.

### Grading

Provided in the [`LIVE_TASK.md`](LIVE_TASK.md) file


## AI agents

You are allowed to use AI agents to implement the **Java code** from your specification (crawler design, OpenAPI specification).

Design the **point-carrying work yourself**: Structure of the simple crawler and multithreaded crawler, and required experiments with these crawlers. Design the API specification and provide a rationale to justify it. Test your implemented search engine.