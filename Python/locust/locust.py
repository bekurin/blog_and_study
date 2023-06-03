from locust import TaskSet, task, HttpUser, between
import random

class User(HttpUser):
    wait_time = between(0, 0)

    @task
    def path_variable(self):
        id = random.randint(1, 10000)
        self.client.get("/validate/path-variable/" + str(id))
        
    @task
    def request_params(self):
        id = random.randint(1, 10000)
        self.client.get("/validate/request-params?id=" + str(id))        

    @task
    def request_body(self):
        id = random.randint(1, 10000)
        self.client.post(
            "/validate/request-body", json={"id": id, "message": "fixed message"})
