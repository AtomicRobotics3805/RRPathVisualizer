import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import util.trajectories.ParallelTrajectory

@Suppress("unused", "MemberVisibilityCanBePrivate")
object TrajectoryFactory {
    lateinit var carouselStartPose: Pose2d
    lateinit var farParkStartPose: Pose2d
    lateinit var closeParkStartPose: Pose2d
    lateinit var hubFrontStartPose: Pose2d
    lateinit var hubTopStartPose: Pose2d
    lateinit var simpleCarouselStartPose: Pose2d

    lateinit var startToHubFront: ParallelTrajectory
    lateinit var startToHubTop: ParallelTrajectory

    lateinit var startToCarousel: ParallelTrajectory

    lateinit var startToParkFar: ParallelTrajectory
    lateinit var startToParkClose: ParallelTrajectory

    lateinit var carouselToHubFront: ParallelTrajectory
    lateinit var carouselToHubBottom: ParallelTrajectory

    lateinit var hubFrontToPark: ParallelTrajectory
    lateinit var hubTopToWarehouseIn: ParallelTrajectory
    lateinit var warehouseInToParkIn: ParallelTrajectory
    lateinit var hubTopToParkOut: ParallelTrajectory
    lateinit var hubBottomToWarehouse: ParallelTrajectory
    lateinit var warehouseToParkIn: ParallelTrajectory
    lateinit var hubBottomToParkOut: ParallelTrajectory
    lateinit var hubBottomToStorageUnit: ParallelTrajectory

    lateinit var carouselToPark: ParallelTrajectory
    lateinit var testTrajectory: ParallelTrajectory

    lateinit var simpleStartToHub1: ParallelTrajectory
    lateinit var simpleStartToHub2: ParallelTrajectory
    lateinit var simpleStartToHub3: ParallelTrajectory
    lateinit var simpleHubToCarousel1: ParallelTrajectory
    lateinit var simpleHubToCarousel2: ParallelTrajectory
    lateinit var simpleCarouselToPark1: ParallelTrajectory
    lateinit var simpleCarouselToPark2: ParallelTrajectory

    fun initializeStartPositions() {
        simpleCarouselStartPose = Pose2d(-36.0, 63.0.switchColor, 90.0.switchColorAngle.toRadians)
        carouselStartPose = Pose2d(-36.0, 63.0.switchColor, (if (Constants.color == Constants.Color.BLUE) 0.0 else 180.0).switchColorAngle.toRadians)
        farParkStartPose = Pose2d(-36.0, 63.0.switchColor, 180.0.switchColorAngle.toRadians)
        closeParkStartPose = Pose2d(6.0, 63.0.switchColor, 180.0.switchColorAngle.toRadians)
        hubFrontStartPose = Pose2d(-12.0, 63.0.switchColor, 90.0.switchColorAngle.toRadians)
        hubTopStartPose = Pose2d(12.0, 63.0.switchColor, 0.0.switchColorAngle.toRadians)
    }

    fun initializeTrajectories() {
        testTrajectory = MecanumDrive.trajectoryBuilder(Pose2d(), 90.0.toRadians)
            //.splineToSplineHeading(Pose2d(0.0, 20.0, 0.0.toRadians), 0.0.toRadians)
            .splineToSplineHeading(Pose2d(20.0, 20.0, 180.0.toRadians), 0.0.toRadians)
            .build()

        startToHubFront = MecanumDrive.trajectoryBuilder(
            hubFrontStartPose,
            hubFrontStartPose.heading + 180.0.switchColorAngle.toRadians
        )
            .back(21.0)
            .build()
        startToHubTop = MecanumDrive.trajectoryBuilder(
            hubTopStartPose,
            hubTopStartPose.heading + 270.0.switchColorAngle.toRadians
        )
            .splineToSplineHeading(Pose2d(12.0, 28.0.switchColor, 0.0.switchColorAngle.toRadians), 270.0.switchColorAngle.toRadians)
            .splineToConstantHeading(Vector2d(7.0, 24.0.switchColor), 225.0.switchColorAngle.toRadians)
            .build()
        startToCarousel = if (Constants.color == Constants.Color.BLUE)
            MecanumDrive.trajectoryBuilder(
                carouselStartPose,
                carouselStartPose.heading + 220.0.switchColorAngle.toRadians
            )
                .splineToSplineHeading(Pose2d(-57.0, 59.0.switchColor, 100.0.switchColorAngle.toRadians), 180.0.switchColorAngle.toRadians)
                .build()
        else
            MecanumDrive.trajectoryBuilder(
                carouselStartPose,
                carouselStartPose.heading + 0.0.switchColorAngle.toRadians
            )
                .splineToConstantHeading(Vector2d(-57.0, 59.0.switchColor), 175.0.switchColorAngle.toRadians)
                .build()

        startToParkFar = MecanumDrive.trajectoryBuilder(
            farParkStartPose,
            farParkStartPose.heading - 184.0.switchColorAngle.toRadians
        )
            .splineToConstantHeading(Vector2d(40.0, 61.0.switchColor), 0.0.switchColorAngle.toRadians)
            .build()
        startToParkClose = MecanumDrive.trajectoryBuilder(
            closeParkStartPose,
            closeParkStartPose.heading - 190.0.switchColorAngle.toRadians
        )
            .splineToConstantHeading(Vector2d(40.0, 61.0.switchColor), 0.0.switchColorAngle.toRadians)
            .build()

        carouselToHubFront = MecanumDrive.trajectoryBuilder(
            startToCarousel.trajectory.end(),
            startToCarousel.trajectory.end().heading + 180.0.switchColorAngle.toRadians
        )
            .splineToSplineHeading(Pose2d(-12.0, 52.0.switchColor, 90.0.switchColorAngle.toRadians), 320.0.switchColorAngle.toRadians)
            .build()
        carouselToHubBottom = if (Constants.color == Constants.Color.BLUE)
            MecanumDrive.trajectoryBuilder(
                startToCarousel.trajectory.end(),
                startToCarousel.trajectory.end().heading + 180.0.toRadians
            )
                .splineToSplineHeading(Pose2d(-33.0, 24.0.switchColor, 180.0.switchColorAngle.toRadians), 320.0.switchColorAngle.toRadians)
                .build()
        else
            MecanumDrive.trajectoryBuilder(
                startToCarousel.trajectory.end(),
                startToCarousel.trajectory.end().heading + 270.0.toRadians
            )
                .splineToSplineHeading(Pose2d(-33.0, 24.0.switchColor, 180.0.switchColorAngle.toRadians), 320.0.switchColorAngle.toRadians)
                .build()

        hubFrontToPark = MecanumDrive.trajectoryBuilder(
            carouselToHubFront.trajectory.end(),
            carouselToHubFront.trajectory.end().heading + 270.0.switchColorAngle.toRadians
        )
            .splineToSplineHeading(Pose2d(20.0, 42.0.switchColor, 0.0.switchColorAngle.toRadians), 0.0.switchColorAngle.toRadians)
            .splineToSplineHeading(Pose2d(40.0, 42.0.switchColor, 0.0.switchColorAngle.toRadians), 0.0.switchColorAngle.toRadians)
            .build()
        hubTopToWarehouseIn = MecanumDrive.trajectoryBuilder(
            startToHubTop.trajectory.end(),
            startToHubTop.trajectory.end().heading + 90.0.switchColorAngle.toRadians
        )
            .splineToSplineHeading(Pose2d(7.0, 40.0.switchColor, 0.0.switchColorAngle.toRadians), 90.0.switchColorAngle.toRadians)
            .build()
        warehouseInToParkIn = MecanumDrive.trajectoryBuilder(
            hubTopToWarehouseIn.trajectory.end(),
            hubTopToWarehouseIn.trajectory.end().heading
        )
            .splineToConstantHeading(Vector2d(60.0, 40.0.switchColor), 0.0.switchColorAngle.toRadians)
            .splineToConstantHeading(Vector2d(60.0, 50.0.switchColor), 90.0.switchColorAngle.toRadians)
            .build()

        hubTopToParkOut = MecanumDrive.trajectoryBuilder(
            startToHubTop.trajectory.end(),
            startToHubTop.trajectory.end().heading + 90.0.switchColorAngle.toRadians
        )
            .splineToSplineHeading(Pose2d(7.0, 53.0.switchColor, 0.0.switchColorAngle.toRadians), 90.0.switchColorAngle.toRadians)
            .splineToConstantHeading(Vector2d(40.0, 61.0.switchColor), 0.0.switchColorAngle.toRadians)
            .build()
        hubBottomToWarehouse = MecanumDrive.trajectoryBuilder(
            carouselToHubBottom.trajectory.end(),
            carouselToHubBottom.trajectory.end().heading + 270.0.switchColorAngle.toRadians
        )
            .splineToSplineHeading(Pose2d(-30.0, 38.0.switchColor, 180.0.switchColorAngle.toRadians), 90.0.switchColorAngle.toRadians)
            .splineToConstantHeading(Vector2d(-12.0, 48.0.switchColor), 0.0.switchColorAngle.toRadians)
            .splineToConstantHeading(Vector2d(8.0, 44.0.switchColor), 0.0.switchColorAngle.toRadians)
            .build()
        warehouseToParkIn = MecanumDrive.trajectoryBuilder(
            hubBottomToWarehouse.trajectory.end(),
            hubBottomToWarehouse.trajectory.end().heading + 180.0.switchColorAngle.toRadians
        )
            .splineToConstantHeading(Vector2d(60.0, 44.0.switchColor), 0.0.switchColorAngle.toRadians)
            .splineToConstantHeading(Vector2d(60.0, 52.0.switchColor), 90.0.switchColorAngle.toRadians)
            .build()

        hubBottomToParkOut = MecanumDrive.trajectoryBuilder(
            carouselToHubBottom.trajectory.end(),
            carouselToHubBottom.trajectory.end().heading + 270.0.switchColorAngle.toRadians
        )
            .splineToSplineHeading(Pose2d(-30.0, 36.0.switchColor, 180.0.switchColorAngle.toRadians), 90.0.switchColorAngle.toRadians)
            .splineToConstantHeading(Vector2d(62.0, 61.0.switchColor), 0.0.switchColorAngle.toRadians)
            .build()
        hubBottomToStorageUnit = MecanumDrive.trajectoryBuilder(
            carouselToHubBottom.trajectory.end(),
            carouselToHubBottom.trajectory.end().heading + 25.0.switchColorAngle.toRadians
        )
            .lineToConstantHeading(Vector2d(-63.0, 35.0.switchColor))
            .build()

        carouselToPark = MecanumDrive.trajectoryBuilder(
            startToCarousel.trajectory.end(),
            startToCarousel.trajectory.end().heading + if (Constants.color == Constants.Color.BLUE) 180.0.switchColorAngle.toRadians else 150.0.switchColorAngle.toRadians
        )
            .splineToSplineHeading(Pose2d(40.0, 61.0.switchColor, 0.0.switchColorAngle.toRadians), 0.0.switchColorAngle.toRadians)
            .build()

        simpleStartToHub1 = MecanumDrive.trajectoryBuilder(simpleCarouselStartPose, simpleCarouselStartPose.heading)
            .back(19.0)
            .build()
        simpleStartToHub2 = MecanumDrive.trajectoryBuilder(
            Pose2d(
                simpleStartToHub1.trajectory.end().vec(),
                simpleStartToHub1.trajectory.end().heading + 270.0.switchColorAngle.toRadians
            ), simpleStartToHub1.trajectory.end().heading + 270.0.switchColorAngle.toRadians
        )
            .forward(22.0)
            .build()

        simpleHubToCarousel1 = MecanumDrive.trajectoryBuilder(
            Pose2d(
                simpleStartToHub2.trajectory.end().vec(),
                simpleStartToHub2.trajectory.end().heading + (if (Constants.color == Constants.Color.BLUE) 174.0 else 170.0).switchColorAngle.toRadians
            ),
            simpleStartToHub2.trajectory.end().heading + (if (Constants.color == Constants.Color.BLUE) 174.0 else 170.0).switchColorAngle.toRadians
        )
            .forward(if (Constants.color == Constants.Color.BLUE) 44.0 else 47.0)
            .build()
        simpleHubToCarousel2 = MecanumDrive.trajectoryBuilder(
            Pose2d(
                simpleHubToCarousel1.trajectory.end().vec(),
                simpleHubToCarousel1.trajectory.end().heading + 308.0.switchColorAngle.toRadians
            ), simpleHubToCarousel1.trajectory.end().heading + 307.0.switchColorAngle.toRadians
        )
            .forward(4.0)
            .build()

        simpleCarouselToPark1 = if (Constants.color == Constants.Color.BLUE)
            MecanumDrive.trajectoryBuilder(
                Pose2d(
                    simpleHubToCarousel2.trajectory.end().vec(),
                    simpleHubToCarousel2.trajectory.end().heading + 0.0.switchColorAngle.toRadians
                ), simpleHubToCarousel2.trajectory.end().heading + 0.0.switchColorAngle.toRadians
            )
                .back(15.0)
                .build()
        else
            MecanumDrive.trajectoryBuilder(
                Pose2d(
                    simpleHubToCarousel1.trajectory.end().vec(),
                    135.0.switchColorAngle.toRadians
                ), 135.0.switchColorAngle.toRadians
            )
                .back(15.0)
                .build()
        simpleCarouselToPark2 = MecanumDrive.trajectoryBuilder(
            Pose2d(
                simpleCarouselToPark1.trajectory.end().vec(),
                (if (Constants.color == Constants.Color.BLUE) 182.0 else 182.0).switchColorAngle.toRadians
            ), (if (Constants.color == Constants.Color.BLUE) 176.0 else 182.0).switchColorAngle.toRadians
        )
            .back(if (Constants.color == Constants.Color.BLUE) 164.0 else 158.0)
            .build()
    }

    val Double.toRadians get() = (Math.toRadians(this))
    val Double.switchColorAngle get () = (if (Constants.color == Constants.Color.BLUE) this else 360 - this)
    val Double.switchColor get () = (if (Constants.color == Constants.Color.BLUE) this else this * -1)
}
/*import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import util.trajectories.ParallelTrajectory

@Suppress("unused", "MemberVisibilityCanBePrivate")
object TrajectoryFactory {
    lateinit var outsideWarehouseStartPose: Pose2d
    lateinit var carouselStartPose: Pose2d
    lateinit var farParkStartPose: Pose2d
    lateinit var closeParkStartPose: Pose2d
    lateinit var hubFrontStartPose: Pose2d
    lateinit var hubTopStartPose: Pose2d
    lateinit var simpleCarouselStartPose: Pose2d

    lateinit var startToInsideWarehouse: ParallelTrajectory
    lateinit var insideWarehouseToOutsideWarehouse: ParallelTrajectory
    lateinit var outsideWarehouseToInsideWarehouse: ParallelTrajectory

    lateinit var startToHubFront: ParallelTrajectory
    lateinit var startToHubTop: ParallelTrajectory

    lateinit var startToCarousel: ParallelTrajectory

    lateinit var startToParkFar: ParallelTrajectory
    lateinit var startToParkClose: ParallelTrajectory

    lateinit var carouselToHubFront: ParallelTrajectory
    lateinit var carouselToHubBottom: ParallelTrajectory

    lateinit var hubFrontToWarehouse: ParallelTrajectory
    lateinit var hubFrontToPark: ParallelTrajectory
    lateinit var hubTopToParkIn: ParallelTrajectory
    lateinit var hubTopToParkOut: ParallelTrajectory
    lateinit var hubBottomToWarehouse: ParallelTrajectory
    lateinit var warehouseToParkIn: ParallelTrajectory
    lateinit var hubBottomToParkOut: ParallelTrajectory
    lateinit var hubBottomToParkClose: ParallelTrajectory

    lateinit var warehouseToHubFront: ParallelTrajectory
    lateinit var secondHubFrontToWarehouse: ParallelTrajectory
    lateinit var hubFrontToCarousel: ParallelTrajectory
    lateinit var carouselToParkClose: ParallelTrajectory

    lateinit var carouselToPark: ParallelTrajectory
    lateinit var testTrajectory: ParallelTrajectory

    lateinit var simpleStartToHub1: ParallelTrajectory
    lateinit var simpleStartToHub2: ParallelTrajectory
    lateinit var simpleStartToHub3: ParallelTrajectory
    lateinit var simpleHubToCarousel1: ParallelTrajectory
    lateinit var simpleHubToCarousel2: ParallelTrajectory
    lateinit var simpleCarouselToPark1: ParallelTrajectory
    lateinit var simpleCarouselToPark2: ParallelTrajectory

    fun initializeStartPositions() {
        outsideWarehouseStartPose = Pose2d(12.0, 65.5.switchColor, 0.0.switchColorAngle.toRadians)
        simpleCarouselStartPose = Pose2d(-36.0, 63.0.switchColor, 90.0.switchColorAngle.toRadians)
        carouselStartPose = Pose2d(-36.0, 63.0.switchColor, (if (Constants.color == Constants.Color.BLUE) 0.0 else 180.0).switchColorAngle.toRadians)
        farParkStartPose = Pose2d(-36.0, 63.0.switchColor, 180.0.switchColorAngle.toRadians)
        closeParkStartPose = Pose2d(6.0, 63.0.switchColor, 180.0.switchColorAngle.toRadians)
        hubFrontStartPose = Pose2d(-12.0, 63.0.switchColor, 90.0.switchColorAngle.toRadians)
        hubTopStartPose = Pose2d(6.0, 63.0.switchColor, 90.0.switchColorAngle.toRadians)
    }

    fun initializeTrajectories() {
        startToInsideWarehouse = MecanumDrive.trajectoryBuilder(outsideWarehouseStartPose, 0.0.toRadians)
            .forward(28.0)
            .build()
        insideWarehouseToOutsideWarehouse = MecanumDrive.trajectoryBuilder(startToInsideWarehouse.trajectory.end(), 0.0.toRadians)
            .back(23.0)
            .build()
        outsideWarehouseToInsideWarehouse = MecanumDrive.trajectoryBuilder(insideWarehouseToOutsideWarehouse.trajectory.end(), 0.0.toRadians)
            .forward(23.0)
            .build()

        testTrajectory = MecanumDrive.trajectoryBuilder(Pose2d(), 90.0.toRadians)
            //.splineToSplineHeading(Pose2d(0.0, 20.0, 0.0.toRadians), 0.0.toRadians)
            .splineToSplineHeading(Pose2d(20.0, 20.0, 180.0.toRadians), 0.0.toRadians)
            .build()

        startToHubFront = MecanumDrive.trajectoryBuilder(hubFrontStartPose, hubFrontStartPose.heading + 180.0.switchColorAngle.toRadians)
            .back(21.0)
            .build()
        startToHubTop = MecanumDrive.trajectoryBuilder(hubTopStartPose, hubFrontStartPose.heading + 180.0.switchColorAngle.toRadians)
            .splineToSplineHeading(Pose2d(6.0, 24.0.switchColor, 0.0.switchColorAngle.toRadians), 270.0.switchColorAngle.toRadians)
            .build()
        startToCarousel = if (Constants.color == Constants.Color.BLUE)
            MecanumDrive.trajectoryBuilder(carouselStartPose, carouselStartPose.heading + 220.0.switchColorAngle.toRadians)
                .splineToSplineHeading(Pose2d(-54.0, 59.0.switchColor, 100.0.switchColorAngle.toRadians), 180.0.switchColorAngle.toRadians)
                .build()
        else
            MecanumDrive.trajectoryBuilder(carouselStartPose, carouselStartPose.heading + 0.0.switchColorAngle.toRadians)
                .splineToConstantHeading(Vector2d(-53.5, 62.0.switchColor), 175.0.switchColorAngle.toRadians)
                .build()

        startToParkFar = MecanumDrive.trajectoryBuilder(farParkStartPose, farParkStartPose.heading - 184.0.switchColorAngle.toRadians)
            .splineToConstantHeading(Vector2d(40.0, 61.0.switchColor), 0.0.switchColorAngle.toRadians)
            .build()
        startToParkClose = MecanumDrive.trajectoryBuilder(closeParkStartPose, closeParkStartPose.heading - 180.0.switchColorAngle.toRadians)
            .splineToConstantHeading(Vector2d(40.0, 61.0.switchColor), 0.0.switchColorAngle.toRadians)
            .build()

        carouselToHubFront = MecanumDrive.trajectoryBuilder(startToCarousel.trajectory.end(), startToCarousel.trajectory.end().heading + 180.0.switchColorAngle.toRadians)
            .splineToSplineHeading(Pose2d(-12.0, 44.0.switchColor, 90.0.switchColorAngle.toRadians), 320.0.switchColorAngle.toRadians)
            .build()
        carouselToHubBottom = if (Constants.color == Constants.Color.BLUE)
            MecanumDrive.trajectoryBuilder(startToCarousel.trajectory.end(), startToCarousel.trajectory.end().heading + 180.0.toRadians)
                .splineToSplineHeading(Pose2d(-30.0, 24.0.switchColor, 180.0.switchColorAngle.toRadians), 320.0.switchColorAngle.toRadians)
                .build()
        else
            MecanumDrive.trajectoryBuilder(startToCarousel.trajectory.end(), startToCarousel.trajectory.end().heading + 270.0.toRadians)
                .splineToSplineHeading(Pose2d(-30.0, 24.0.switchColor, 180.0.switchColorAngle.toRadians), 320.0.switchColorAngle.toRadians)
                .build()

        hubFrontToPark = MecanumDrive.trajectoryBuilder(carouselToHubFront.trajectory.end(), carouselToHubFront.trajectory.end().heading + 270.0.switchColorAngle.toRadians)
            .splineToSplineHeading(Pose2d(20.0, 42.0.switchColor, 0.0.switchColorAngle.toRadians), 0.0.switchColorAngle.toRadians)
            .splineToSplineHeading(Pose2d(60.0, 42.0.switchColor, 0.0.switchColorAngle.toRadians), 0.0.switchColorAngle.toRadians)
            .build()
        hubTopToParkIn = MecanumDrive.trajectoryBuilder(startToHubTop.trajectory.end(), startToHubTop.trajectory.end().heading + 90.0.switchColorAngle.toRadians)
            .splineToSplineHeading(Pose2d(6.0, 32.0.switchColor, 0.0.switchColorAngle.toRadians), 90.0.switchColorAngle.toRadians)
            .splineToConstantHeading(Vector2d(40.0, 40.0.switchColor), 0.0.switchColorAngle.toRadians)
            .build()
        hubTopToParkOut = MecanumDrive.trajectoryBuilder(startToHubTop.trajectory.end(), startToHubTop.trajectory.end().heading + 90.0.switchColorAngle.toRadians)
            .splineToSplineHeading(Pose2d(6.0, 53.0.switchColor, 0.0.switchColorAngle.toRadians), 90.0.switchColorAngle.toRadians)
            .splineToConstantHeading(Vector2d(40.0, 61.0.switchColor), 0.0.switchColorAngle.toRadians)
            .build()
        hubBottomToWarehouse = MecanumDrive.trajectoryBuilder(carouselToHubBottom.trajectory.end(),
            carouselToHubBottom.trajectory.end().heading + 270.0.switchColorAngle.toRadians)
            .splineToSplineHeading(Pose2d(-30.0, 38.0.switchColor, 180.0.switchColorAngle.toRadians), 90.0.switchColorAngle.toRadians)
            .splineToConstantHeading(Vector2d(-12.0, 48.0.switchColor), 0.0.switchColorAngle.toRadians)
            .splineToConstantHeading(Vector2d(13.0, 49.0.switchColor), 0.0.switchColorAngle.toRadians)
            .build()
        warehouseToParkIn = MecanumDrive.trajectoryBuilder(hubBottomToWarehouse.trajectory.end(),
            hubBottomToWarehouse.trajectory.end().heading + 0.0.switchColorAngle.toRadians)
            .splineToConstantHeading(Vector2d(60.0, 49.0.switchColor), 0.0.switchColorAngle.toRadians)
            .splineToConstantHeading(Vector2d(60.0, 52.0.switchColor), 90.0.switchColorAngle.toRadians)
            .build()
        hubBottomToParkOut = MecanumDrive.trajectoryBuilder(carouselToHubBottom.trajectory.end(), carouselToHubBottom.trajectory.end().heading + 270.0.switchColorAngle.toRadians)
            .splineToSplineHeading(Pose2d(-30.0, 36.0.switchColor, 180.0.switchColorAngle.toRadians), 90.0.switchColorAngle.toRadians)
            .splineToConstantHeading(Vector2d(40.0, 60.0.switchColor), 0.0.switchColorAngle.toRadians)
            .build()
        hubBottomToParkClose = MecanumDrive.trajectoryBuilder(carouselToHubBottom.trajectory.end(), carouselToHubBottom.trajectory.end().heading + 270.0.switchColorAngle.toRadians)
            .splineToConstantHeading(Vector2d(40.0, 60.0.switchColor), 0.0.switchColorAngle.toRadians)
            .build()

        hubFrontToWarehouse = MecanumDrive.trajectoryBuilder(startToHubFront.trajectory.end(), startToHubFront.trajectory.end().heading - 35.0.switchColorAngle.toRadians)
            .splineToSplineHeading(Pose2d(7.0, 59.0.switchColor, 0.0.switchColorAngle.toRadians), 35.0.switchColorAngle.toRadians)
            .splineToConstantHeading(Vector2d(39.0, 65.0.switchColor), 0.0.switchColorAngle.toRadians)
            .build()

        warehouseToHubFront = MecanumDrive.trajectoryBuilder(hubFrontToWarehouse.trajectory.end(), hubFrontToWarehouse.trajectory.end().heading - 180.0.switchColorAngle.toRadians)
            .splineToSplineHeading(Pose2d(15.0, 65.0.switchColor, 0.0.switchColorAngle.toRadians), 180.0.switchColorAngle.toRadians)
            .splineToConstantHeading(Vector2d(10.0, 60.0.switchColor), 225.0.switchColorAngle.toRadians)
            .splineToSplineHeading(Pose2d(-4.0, 43.0.switchColor, 60.0.switchColorAngle.toRadians), 230.0.switchColorAngle.toRadians)
            .build()
        secondHubFrontToWarehouse = MecanumDrive.trajectoryBuilder(warehouseToHubFront.trajectory.end(), warehouseToHubFront.trajectory.end().heading - 15.0.switchColorAngle.toRadians)
            .splineToSplineHeading(Pose2d(8.0, 58.0.switchColor, 0.0.switchColorAngle.toRadians), 45.0.switchColorAngle.toRadians)
            .splineToConstantHeading(Vector2d(39.0, 65.0.switchColor), 0.0.switchColorAngle.toRadians)
            .build()
        hubFrontToCarousel = if (Constants.color == Constants.Color.BLUE)
            MecanumDrive.trajectoryBuilder(warehouseToHubFront.trajectory.end(), warehouseToHubFront.trajectory.end().heading + 105.0.switchColorAngle.toRadians)
                .splineToSplineHeading(Pose2d(-55.0, 59.0.switchColor, 100.0.switchColorAngle.toRadians), 180.0.switchColorAngle.toRadians)
                .build()
        else
            MecanumDrive.trajectoryBuilder(warehouseToHubFront.trajectory.end(), warehouseToHubFront.trajectory.end().heading - 0.0.switchColorAngle.toRadians)
                .splineToSplineHeading(Pose2d(-30.0, 24.0.switchColor, 180.0.switchColorAngle.toRadians), 320.0.switchColorAngle.toRadians)
                .build()
        carouselToParkClose = if (Constants.color == Constants.Color.BLUE)
            MecanumDrive.trajectoryBuilder(hubFrontToCarousel.trajectory.end(), hubFrontToCarousel.trajectory.end().heading + 160.0.switchColorAngle.toRadians)
                .splineToSplineHeading(Pose2d(-58.0, 37.0.switchColor, 90.0.switchColorAngle.toRadians), 270.0.switchColorAngle.toRadians)
                .build()
        else
            MecanumDrive.trajectoryBuilder(warehouseToHubFront.trajectory.end(), warehouseToHubFront.trajectory.end().heading - 0.0.switchColorAngle.toRadians)
                .splineToSplineHeading(Pose2d(-30.0, 24.0.switchColor, 180.0.switchColorAngle.toRadians), 320.0.switchColorAngle.toRadians)
                .build()

        carouselToPark = MecanumDrive.trajectoryBuilder(startToCarousel.trajectory.end(),
            startToCarousel.trajectory.end().heading + if (Constants.color == Constants.Color.BLUE) 180.0.switchColorAngle.toRadians else 150.0.switchColorAngle.toRadians)
            .splineToSplineHeading(Pose2d(40.0, 61.0.switchColor, 0.0.switchColorAngle.toRadians), 0.0.switchColorAngle.toRadians)
            .build()

        simpleStartToHub1 = MecanumDrive.trajectoryBuilder(simpleCarouselStartPose, simpleCarouselStartPose.heading)
            .back(19.0)
            .build()
        simpleStartToHub2 = MecanumDrive.trajectoryBuilder(Pose2d(simpleStartToHub1.trajectory.end().vec(), simpleStartToHub1.trajectory.end().heading + 270.0.switchColorAngle.toRadians), simpleStartToHub1.trajectory.end().heading + 270.0.switchColorAngle.toRadians)
            .forward(22.0)
            .build()

        simpleHubToCarousel1 = MecanumDrive.trajectoryBuilder(Pose2d(simpleStartToHub2.trajectory.end().vec(), simpleStartToHub2.trajectory.end().heading + (if (Constants.color == Constants.Color.BLUE) 174.0 else 170.0).switchColorAngle.toRadians), simpleStartToHub2.trajectory.end().heading + (if (Constants.color == Constants.Color.BLUE) 174.0 else 170.0).switchColorAngle.toRadians)
            .forward(if (Constants.color == Constants.Color.BLUE) 44.0 else 47.0)
            .build()
        simpleHubToCarousel2 = MecanumDrive.trajectoryBuilder(Pose2d(simpleHubToCarousel1.trajectory.end().vec(), simpleHubToCarousel1.trajectory.end().heading + 308.0.switchColorAngle.toRadians), simpleHubToCarousel1.trajectory.end().heading + 307.0.switchColorAngle.toRadians)
            .forward(4.0)
            .build()

        simpleCarouselToPark1 = if (Constants.color == Constants.Color.BLUE)
            MecanumDrive.trajectoryBuilder(Pose2d(simpleHubToCarousel2.trajectory.end().vec(), simpleHubToCarousel2.trajectory.end().heading + 0.0.switchColorAngle.toRadians), simpleHubToCarousel2.trajectory.end().heading + 0.0.switchColorAngle.toRadians)
                .back(15.0)
                .build()
        else
            MecanumDrive.trajectoryBuilder(Pose2d(simpleHubToCarousel1.trajectory.end().vec(), 135.0.switchColorAngle.toRadians), 135.0.switchColorAngle.toRadians)
                .back(15.0)
                .build()
        simpleCarouselToPark2 = MecanumDrive.trajectoryBuilder(Pose2d(simpleCarouselToPark1.trajectory.end().vec(), (if (Constants.color == Constants.Color.BLUE) 182.0 else 182.0).switchColorAngle.toRadians), (if (Constants.color == Constants.Color.BLUE) 176.0 else 182.0).switchColorAngle.toRadians)
            .back(if (Constants.color == Constants.Color.BLUE) 164.0 else 158.0)
            .build()
    }

    val Double.toRadians get() = (Math.toRadians(this))
    val Double.switchColorAngle get () = (if (Constants.color == Constants.Color.BLUE) this else 360 - this)
    val Double.switchColor get () = (if (Constants.color == Constants.Color.BLUE) this else this * -1)
}*/