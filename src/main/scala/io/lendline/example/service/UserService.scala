package io.lendline.example.service

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class UserService (userDao: UserDao)(implicit ec: ExecutionContext) {

  def getUser(userId: Long): Future[Option[User]] = {
    userDao.get(userId)
  }

  def addUser(user: User): Future[Long] = {
    userDao.add(user)
  }
}
