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

import io.onema.beenpwned4s.HaveIBeenPwnedApi._
import requests.Response


class HaveIBeenPwnedApiRequests extends HaveIBeenPwnedApi {

  //--- Methods ---
  def pwnedPassword(hashedPassword: String): Response = {
    val firstFive = hashedPassword.substring(0, 5)
    val uri = HaveIBeenPwnedApi.passwordUrl + s"/range/$firstFive"
    request(uri)
  }

  def breachedEmail(email: String): Response = {
    val uri = HaveIBeenPwnedApi.emailUrl + s"/${HaveIBeenPwnedApi.version}/breachedaccount/${email.urlEncoded}"
    request(uri)
  }

  def breaches(siteName: Option[String] = None): Response = {
    val uri = HaveIBeenPwnedApi.emailUrl + s"/${HaveIBeenPwnedApi.version}/breaches/${siteName.getOrElse("").urlEncoded}"
    request(uri)
  }

  def pastedEmail(email: String): Response = {
    val uri = HaveIBeenPwnedApi.emailUrl + s"/${HaveIBeenPwnedApi.version}/pasteaccount/${email.urlEncoded}"
    request(uri)
  }

  private def request(uri: String): Response = {
    requests.get(
      uri,
      headers = Map("user-agent" -> HaveIBeenPwnedApi.userAgent)
    )
  }
}
