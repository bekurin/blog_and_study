from locust import TaskSet, task, HttpUser, between


class User(HttpUser):
    wait_time = 0

    @task
    def index(self):
        self.client.get("/")

    @task(3)
    def login(self):
        self.client.post(
            "/login", json={"username": "testuser", "password": "1111"})
