/**
  * This file is part of the ONEMA beenpwned4s Package.
  * For the full copyright and license information,
  * please view the LICENSE file that was distributed
  * with this source code.
  *
  * copyright (c) 2019, Juan Manuel Torres (http://onema.io)
  *
  * @author Juan Manuel Torres <software@onema.io>
  */

import io.onema.beenpwned4s.HaveIBeenPwnedApi._
import io.onema.beenpwned4s.{HaveIBeenPwnedApi, PwnedClient}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}
import requests.{Response, ResponseBlob}


class PwnedClientTest extends FlatSpec with Matchers with MockFactory with BeforeAndAfter {
  "an email " should "return a json payload " in {
    // Arrange
    val responseText = """[{"Name":"000webhost","Title":"000webhost","Domain":"000webhost.com","BreachDate":"2015-03-01","AddedDate":"2015-10-26T23:35:45Z","ModifiedDate":"2017-12-10T21:44:27Z","PwnCount":14936670,"Description":"In approximately March 2015, the free web hosting provider <a href=\"http://www.troyhunt.com/2015/10/breaches-traders-plain-text-passwords.html\" target=\"_blank\" rel=\"noopener\">000webhost suffered a major data breach</a> that exposed almost 15 million customer records. The data was sold and traded before 000webhost was alerted in October. The breach included names, email addresses and plain text passwords.","LogoPath":"https://haveibeenpwned.com/Content/Images/PwnedLogos/000webhost.png","DataClasses":["Email addresses","IP addresses","Names","Passwords"],"IsVerified":true,"IsFabricated":false,"IsSensitive":false,"IsRetired":false,"IsSpamList":false},{"Name":"126","Title":"126","Domain":"126.com","BreachDate":"2012-01-01","AddedDate":"2016-10-08T07:46:05Z","ModifiedDate":"2016-10-08T07:46:05Z","PwnCount":6414191,"Description":"In approximately 2012, it's alleged that the Chinese email service known as <a href=\"http://126.com\" target=\"_blank\" rel=\"noopener\">126</a> suffered a data breach that impacted 6.4 million subscribers. Whilst there is evidence that the data is legitimate, due to the difficulty of emphatically verifying the Chinese breach it has been flagged as &quot;unverified&quot;. The data in the breach contains email addresses and plain text passwords. <a href=\"https://www.troyhunt.com/handling-chinese-data-breaches-in-have-i-been-pwned/\" target=\"_blank\" rel=\"noopener\">Read more about Chinese data breaches in Have I been pwned.</a>","LogoPath":"https://haveibeenpwned.com/Content/Images/PwnedLogos/126.png","DataClasses":["Email addresses","Passwords"],"IsVerified":false,"IsFabricated":false,"IsSensitive":false,"IsRetired":false,"IsSpamList":false}]"""
    val response = buildResponse(responseText)
    val email = "foobarbazblah@yahoo.com"
    val mockRequestsApi = mock[HaveIBeenPwnedApi]
    (mockRequestsApi.breachedEmail(_: String)).expects(email).returns(response).once()
    val client = PwnedClient(mockRequestsApi)

    // Act
    val result = client.getEmailBreached(email)

    // Assert
    result.isDefined should be (true)
    result.get.length should be (2)
  }

  "an email " should "return true if found" in {
    // Arrange
    val responseText = """[{"Name":"000webhost","Title":"000webhost","Domain":"000webhost.com","BreachDate":"2015-03-01","AddedDate":"2015-10-26T23:35:45Z","ModifiedDate":"2017-12-10T21:44:27Z","PwnCount":14936670,"Description":"In approximately March 2015, the free web hosting provider <a href=\"http://www.troyhunt.com/2015/10/breaches-traders-plain-text-passwords.html\" target=\"_blank\" rel=\"noopener\">000webhost suffered a major data breach</a> that exposed almost 15 million customer records. The data was sold and traded before 000webhost was alerted in October. The breach included names, email addresses and plain text passwords.","LogoPath":"https://haveibeenpwned.com/Content/Images/PwnedLogos/000webhost.png","DataClasses":["Email addresses","IP addresses","Names","Passwords"],"IsVerified":true,"IsFabricated":false,"IsSensitive":false,"IsRetired":false,"IsSpamList":false},{"Name":"126","Title":"126","Domain":"126.com","BreachDate":"2012-01-01","AddedDate":"2016-10-08T07:46:05Z","ModifiedDate":"2016-10-08T07:46:05Z","PwnCount":6414191,"Description":"In approximately 2012, it's alleged that the Chinese email service known as <a href=\"http://126.com\" target=\"_blank\" rel=\"noopener\">126</a> suffered a data breach that impacted 6.4 million subscribers. Whilst there is evidence that the data is legitimate, due to the difficulty of emphatically verifying the Chinese breach it has been flagged as &quot;unverified&quot;. The data in the breach contains email addresses and plain text passwords. <a href=\"https://www.troyhunt.com/handling-chinese-data-breaches-in-have-i-been-pwned/\" target=\"_blank\" rel=\"noopener\">Read more about Chinese data breaches in Have I been pwned.</a>","LogoPath":"https://haveibeenpwned.com/Content/Images/PwnedLogos/126.png","DataClasses":["Email addresses","Passwords"],"IsVerified":false,"IsFabricated":false,"IsSensitive":false,"IsRetired":false,"IsSpamList":false}]"""
    val response = buildResponse(responseText)
    val email = "foobarbazblah@yahoo.com"
    val mockRequestsApi = mock[HaveIBeenPwnedApi]
    (mockRequestsApi.breachedEmail(_: String)).expects(email).returns(response).once()
    val client = PwnedClient(mockRequestsApi)

    // Act
    val result = client.isEmailBreached(email)

    // Assert
    result should be (true)
  }

  "a password " should "return a payload " in {
    // Arrange
    val responseText = "003D68EB55068C33ACE09247EE4C639306B:3\r\n012C192B2F16F82EA0EB9EF18D9D539B0DD:1\r\n01330C689E5D64F660D6947A93AD634EF8F:1\r\n0198748F3315F40B1A102BF18EEA0194CD9:1\r\n01F9033B3C00C65DBFD6D1DC4D22918F5E9:2\r\n1E4C9B93F3F0682250B6CF8331B7EE68FD8:3645804"
    val response = buildResponse(responseText)
    val password = "password"
    val mockRequestApi = mock[HaveIBeenPwnedApi]
    (mockRequestApi.pwnedPassword(_: String)).expects(password.sha1).returns(response).once()
    val client = PwnedClient(mockRequestApi)

    // Act
    val result = client.getPasswordPwned("password")

    // Assert
    result.isDefined should be (true)
    result.get.count should be (3645804)
  }

  "a password " should "return a true " in {
    // Arrange
    val responseText = "003D68EB55068C33ACE09247EE4C639306B:3\r\n012C192B2F16F82EA0EB9EF18D9D539B0DD:1\r\n01330C689E5D64F660D6947A93AD634EF8F:1\r\n0198748F3315F40B1A102BF18EEA0194CD9:1\r\n01F9033B3C00C65DBFD6D1DC4D22918F5E9:2\r\n1E4C9B93F3F0682250B6CF8331B7EE68FD8:3645804"
    val response = buildResponse(responseText)
    val password = "password"
    val mockRequestApi = mock[HaveIBeenPwnedApi]
    (mockRequestApi.pwnedPassword(_: String)).expects(password.sha1).returns(response).once()
    val client = PwnedClient(mockRequestApi)

    // Act
    val result = client.isPasswordPwned("password")

    // Assert
    result should be (true)
  }

  "a breaches call " should "return a all breaches " in {
    // Arrange
    val responseText = """[{"Name":"000webhost","Title":"000webhost","Domain":"000webhost.com","BreachDate":"2015-03-01","AddedDate":"2015-10-26T23:35:45Z","ModifiedDate":"2017-12-10T21:44:27Z","PwnCount":14936670,"Description":"In approximately March 2015, the free web hosting provider <a href=\"http://www.troyhunt.com/2015/10/breaches-traders-plain-text-passwords.html\" target=\"_blank\" rel=\"noopener\">000webhost suffered a major data breach</a> that exposed almost 15 million customer records. The data was sold and traded before 000webhost was alerted in October. The breach included names, email addresses and plain text passwords.","LogoPath":"https://haveibeenpwned.com/Content/Images/PwnedLogos/000webhost.png","DataClasses":["Email addresses","IP addresses","Names","Passwords"],"IsVerified":true,"IsFabricated":false,"IsSensitive":false,"IsRetired":false,"IsSpamList":false},{"Name":"126","Title":"126","Domain":"126.com","BreachDate":"2012-01-01","AddedDate":"2016-10-08T07:46:05Z","ModifiedDate":"2016-10-08T07:46:05Z","PwnCount":6414191,"Description":"In approximately 2012, it's alleged that the Chinese email service known as <a href=\"http://126.com\" target=\"_blank\" rel=\"noopener\">126</a> suffered a data breach that impacted 6.4 million subscribers. Whilst there is evidence that the data is legitimate, due to the difficulty of emphatically verifying the Chinese breach it has been flagged as &quot;unverified&quot;. The data in the breach contains email addresses and plain text passwords. <a href=\"https://www.troyhunt.com/handling-chinese-data-breaches-in-have-i-been-pwned/\" target=\"_blank\" rel=\"noopener\">Read more about Chinese data breaches in Have I been pwned.</a>","LogoPath":"https://haveibeenpwned.com/Content/Images/PwnedLogos/126.png","DataClasses":["Email addresses","Passwords"],"IsVerified":false,"IsFabricated":false,"IsSensitive":false,"IsRetired":false,"IsSpamList":false}]"""
    val response = buildResponse(responseText)
    val mockRequestsApi = mock[HaveIBeenPwnedApi]
    (mockRequestsApi.breaches(_: Option[String])).expects(None).returns(response).once()
    val client = PwnedClient(mockRequestsApi)

    // Act
    val result = client.getBreaches()

    // Assert
    result.isDefined should be (true)
    result.get.length should be (2)
  }

  private def buildResponse(responseText: String, statusCode: Int = 200, statusText: String = "OK"): Response = {
    val responseBlob = new ResponseBlob(responseText.getBytes)
    Response("https://mock.com", statusCode, statusText, Map(), responseBlob, None)
  }
}
