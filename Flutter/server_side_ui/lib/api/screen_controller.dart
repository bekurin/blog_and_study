import 'package:http/http.dart' as http;

String baseUrl = "http://localhost:8080";

class ScreenController {
  Future<http.Response> fetchMainScreen() async {
    final url = Uri.parse(baseUrl + "/api/v1/main");
    var result = await http.get(url);
    return result;
  }

  Future<http.Response> fetchSignInScreen() async {
    final url = Uri.parse(baseUrl + "/api/v1/sign-in");
    var result = await http.get(url);
    return result;
  }
}
