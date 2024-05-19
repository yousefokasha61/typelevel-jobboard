package com.typelevelproject.jobsboard.playground

import cats.effect.{ExitCode, IO, IOApp}
import doobie.util.transactor.Transactor

object SimplePlayground extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    val xa :Transactor[IO] = Transactor.fromDriverManager[IO](
      "org.postgresql.Driver",
      "jdbc:postgresql://localhost:5432/demo",
      "docker",
      "docker"
    )

    def findAllStudentNames : IO[List[String]] = {
      import doobie.implicits._
      sql"SELECT name FROM students".query[String].to[List].transact(xa)
    }
    findAllStudentNames.map(println).as(ExitCode.Success)
  }
}
