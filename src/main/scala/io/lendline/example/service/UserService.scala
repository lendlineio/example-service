package io.lendline.example.service

import akka.stream.ActorMaterializer
import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class UserService (userDao: UserDao)(implicit ec: ExecutionContext, m : ActorMaterializer) {

  def getUser(userId: Long): Future[Option[User]] = {
    userDao.get(userId)
  }
}
