@file:Suppress("unused")

import com.acmerobotics.roadrunner.geometry.Vector2d
import com.acmerobotics.roadrunner.trajectory.Trajectory
import util.trajectories.ParallelTrajectory

object TrajectoryGen {
    private fun createParallelTrajectory(): ArrayList<ParallelTrajectory> {
        return carouselDuckPath()
    }

    private fun simpleCarouselHubPath(): ArrayList<ParallelTrajectory> {
        return arrayListOf(TrajectoryFactory.simpleStartToHub1, TrajectoryFactory.simpleStartToHub2,
            TrajectoryFactory.simpleHubToCarousel1, TrajectoryFactory.simpleHubToCarousel2,
            TrajectoryFactory.simpleCarouselToPark1, TrajectoryFactory.simpleCarouselToPark2)
    }

    // ordered from simplest to most complicated

    private fun parkClosePath(): ArrayList<ParallelTrajectory> {
        return arrayListOf(TrajectoryFactory.startToParkClose)
    }

    private fun parkFarPath(): ArrayList<ParallelTrajectory> {
        return arrayListOf(TrajectoryFactory.startToParkFar)
    }

    private fun carouselPath(): ArrayList<ParallelTrajectory> {
        return arrayListOf(TrajectoryFactory.startToCarousel, TrajectoryFactory.carouselToPark)
    }

    private fun hubFrontPath(): ArrayList<ParallelTrajectory> {
        return arrayListOf(TrajectoryFactory.startToHubFront, TrajectoryFactory.hubFrontToPark)
    }

    private fun hubTopParkInPath(): ArrayList<ParallelTrajectory> {
        return arrayListOf(TrajectoryFactory.startToHubTop, TrajectoryFactory.hubTopToWarehouseIn, TrajectoryFactory.warehouseInToParkIn)
    }

    private fun hubTopParkOutPath(): ArrayList<ParallelTrajectory> {
        return arrayListOf(TrajectoryFactory.startToHubTop, TrajectoryFactory.hubTopToParkOut)
    }

    private fun carouselHubPath(): ArrayList<ParallelTrajectory> {
        return arrayListOf(TrajectoryFactory.startToCarousel, TrajectoryFactory.carouselToHubFront, TrajectoryFactory.hubFrontToPark)
    }

    private fun carouselDuckPath(): ArrayList<ParallelTrajectory> {
        return arrayListOf(TrajectoryFactory.startToCarousel, TrajectoryFactory.carouselToHubBottom, TrajectoryFactory.hubBottomToDucks, TrajectoryFactory.ducksToHubBottom, TrajectoryFactory.hubBottomToWarehouseMiddle, TrajectoryFactory.warehouseMiddleToPark)
    }

    private fun carouselHubBottomParkInPath(): ArrayList<ParallelTrajectory> {
        return arrayListOf(TrajectoryFactory.startToCarousel, TrajectoryFactory.carouselToHubBottom, TrajectoryFactory.hubBottomToWarehouse, TrajectoryFactory.warehouseToParkIn)
    }

    private fun carouselHubBottomParkOutPath(): ArrayList<ParallelTrajectory> {
        return arrayListOf(TrajectoryFactory.startToCarousel, TrajectoryFactory.carouselToHubBottom, TrajectoryFactory.hubBottomToParkOut)
    }
/*
    private fun carouselTwoFreights(): ArrayList<ParallelTrajectory> {
        return arrayListOf(TrajectoryFactory.startToHubFront, TrajectoryFactory.hubFrontToWarehouse,
            TrajectoryFactory.warehouseToHubFront, TrajectoryFactory.secondHubFrontToWarehouse,
            TrajectoryFactory.warehouseToHubFront, TrajectoryFactory.hubFrontToCarousel,
            TrajectoryFactory.carouselToParkClose)
    }

    private fun mostFreights(): ArrayList<ParallelTrajectory> {
        val arrayList = arrayListOf<ParallelTrajectory>()
        arrayList.add(TrajectoryFactory.startToInsideWarehouse)
        repeat(5) {
            arrayList.add(TrajectoryFactory.insideWarehouseToOutsideWarehouse)
            arrayList.add(TrajectoryFactory.outsideWarehouseToInsideWarehouse)
        }
        return arrayList
    }
*/
    private fun carouselHubBottomStorageUnitPath(): ArrayList<ParallelTrajectory> {
        return arrayListOf(TrajectoryFactory.startToCarousel, TrajectoryFactory.carouselToHubBottom, TrajectoryFactory.hubBottomToStorageUnit)
    }
    fun drawOffbounds() {
        GraphicsUtil.fillRect(Vector2d(0.0, -63.0), 18.0, 18.0) // robot against the wall
    }
    
    fun createTrajectory(): ArrayList<Trajectory> {
        TrajectoryFactory.initializeStartPositions()
        TrajectoryFactory.initializeTrajectories()
        val parallelTrajectories = createParallelTrajectory()
        val trajectories = arrayListOf<Trajectory>()
        for (parallelTrajectory in parallelTrajectories) {
            trajectories.add(parallelTrajectory.trajectory)
        }
        return trajectories
    }
}