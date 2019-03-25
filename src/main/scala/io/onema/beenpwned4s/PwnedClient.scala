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

package io.onema.beenpwned4s

import java.nio.charset.Charset

import io.onema.beenpwned4s.HaveIBeenPwnedApi._
import io.onema.beenpwned4s.PwnedClient._
import io.onema.json.Extensions._
import requests.Response


class PwnedClient (val requestsApi: HaveIBeenPwnedApi) {

  //--- Methods ---
  def isEmailBreached(email: String): Boolean = getEmailBreached(email).nonEmpty

  def getEmailBreached(email: String): Option[Seq[Breach]] = request[Seq[Breach]] {
    requestsApi.breachedEmail(email)
  }

  def getBreaches(siteName: Option[String] = None): Option[Seq[Breach]] = request[Seq[Breach]] {
    requestsApi.breaches(siteName)
  }

  def getPasswordPastes(email: String): Option[Seq[Pastes]] = request {
    requestsApi.pastedEmail(email)
  }

  def isPasswordPwned(pass: String): Boolean = getPasswordPwned(pass).isDefined

  def getPasswordPwned(pass: String): Option[HashPwned] = {
    val hashedPassword = pass.sha1
    val hashSuffix = hashedPassword.substring(5, hashedPassword.length).toUpperCase
    val result: Response = requestsApi.pwnedPassword(hashedPassword)
    if(result.statusCode == 200) {
      result.text.split("\\r\\n").map(x => {
          val split = x.split(":")
          HashPwned(hashSuffix = split(0), count = split(1).toInt)
        })
        .find(hashSuffix == _.hashSuffix)
    } else {
      None
    }
  }

  private def request[T: Manifest](codeBlock: => Response): Option[T] = {
    val result = codeBlock
    if(result.statusCode == 200) {
      val encodedResponse = result.text(Charset.forName("UTF-8"))
      Some(encodedResponse.jsonDecode[T])
    } else {
      None
    }
  }
}

object PwnedClient {

  //--- Apply Methods ---
  def apply(): PwnedClient = new PwnedClient(new HaveIBeenPwnedApiRequests)

  def apply(requestsApi: HaveIBeenPwnedApi): PwnedClient = new PwnedClient(requestsApi)

  //--- Classes ---
  case class Breach(
    Name: String,
    Title: String,
    Domain: String,
    BreachDate: String,
    AddedDate: String,
    ModifiedDate: String,
    PwnCount: Double,
    Description: String,
    LogoPath: String,
    DataClasses: List[String],
    IsVerified: Boolean,
    IsFabricated: Boolean,
    IsSensitive: Boolean,
    IsRetired: Boolean,
    IsSpamList: Boolean
  )

  case class HashPwned(hashSuffix: String, count: Int)

  case class Pastes(Source: String, Id: String, Title: String, Date: String, EmailCount: Double)
}
