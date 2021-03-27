import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumConstraints

class TrajectoryBuild(private val combinedConstraints: MecanumConstraints) {
    private lateinit var poseEstimate: Pose2d

    fun trajectoryBuilder(startPose: Pose2d): TrajectoryBuilder {
        var traj = TrajectoryBuilder(startPose, false, combinedConstraints)
        return traj
    }

    fun trajectoryBuilder(startPose: Pose2d, reversed: Boolean): TrajectoryBuilder {
        return TrajectoryBuilder(startPose, reversed, combinedConstraints)
    }

    fun trajectoryBuilder(startPose: Pose2d, startHeading: Double): TrajectoryBuilder {
        return TrajectoryBuilder(startPose, startHeading, combinedConstraints)
    }
}