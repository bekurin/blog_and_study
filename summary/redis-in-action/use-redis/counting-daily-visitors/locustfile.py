import random
from locust import HttpUser, TaskSet, task, between


class User(HttpUser):
    wait_time = between(0.5, 1.5)
    global version, userCount
    version = "/v1"
    userCount = 100000

    @task(1)
    def visit(self):
        self.client.get(version + "/visits" + "/" + str(random.randint(1, userCount)))
