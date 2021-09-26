import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.acmerobotics.roadrunner.trajectory.Trajectory

object TrajectoryGen {
    enum class Color {
        BLUE,
        RED
    }

    val color = Color.BLUE

    private val startPose = Pose2d(-36.0, 63.0, 180.0.toRadians)

    private val drive = TrajectoryBuild(DriveConstantsComp)

    private val startToCarousel = drive.trajectoryBuilder(startPose, startPose.heading)
        .splineToConstantHeading(Vector2d(-53.5, 62.0), 175.0.toRadians)
        .build()

    private val startToPark = drive.trajectoryBuilder(startPose, true)
        .splineToConstantHeading(Vector2d(40.0, 52.0), 0.0.toRadians)
        .build()

    private val carouselToHub = drive.trajectoryBuilder(startToCarousel.end(), true)
        .splineToSplineHeading(Pose2d(-12.0, 42.0, 270.0.toRadians), 320.0.toRadians)
        .build()

    private val hubToPark = drive.trajectoryBuilder(carouselToHub.end(), carouselToHub.end().heading + 100.0.toRadians)
        .splineTo(Vector2d(40.0, 52.0), 10.0.toRadians)
        .build()

    private val carouselToPark = drive.trajectoryBuilder(startToCarousel.end(), startToCarousel.end().heading)
        .back(95.0)
        .build()


    fun createTrajectory(): ArrayList<Trajectory> {
        return hubPath()
    }

    fun hubPath(): ArrayList<Trajectory> {
        return arrayListOf(startToCarousel, carouselToHub, hubToPark)
    }

    fun carouselPath(): ArrayList<Trajectory> {
        return arrayListOf(startToCarousel, carouselToPark)
    }

    fun parkPath(): ArrayList<Trajectory> {
        return arrayListOf(startToPark)
    }

    fun drawOffbounds() {
        GraphicsUtil.fillRect(Vector2d(0.0, -63.0), 18.0, 18.0) // robot against the wall
    }
}

val Double.toRadians get() = (Math.toRadians(this))

val Double.a get () = (if (TrajectoryGen.color == TrajectoryGen.Color.BLUE) this else 360 - this)

val Double.flip get () = (if (TrajectoryGen.color == TrajectoryGen.Color.BLUE) this else {
    if(this - 180 < 0) this + 180
    this - 180
})

val Double.y get () = (if (TrajectoryGen.color == TrajectoryGen.Color.BLUE) this else this * -1)