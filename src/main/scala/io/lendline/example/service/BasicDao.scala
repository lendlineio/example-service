package io.lendline.example.service

import scala.concurrent.Future

trait BasicDao[T] {
  def add(t: T): Future[Long]
  def get(id: Long) : Future[Option[T]]
  def delete(id: Long) : Future[Int]
  def update(t: T) : Future[Int]
  def create() : Future[Unit]
  def drop() : Future[Unit]
}
