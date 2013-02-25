package team184;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class SoldierRobot extends BaseRobot {
    private SoldierStrategy strategy;
    private RouteStrategy routeStrategy;
    
	public SoldierRobot(RobotController rc, SoldierStrategy strategy, RouteStrategy routeStrategy) {
		super(rc);
		this.strategy = strategy;
		this.setRouteStrategy(routeStrategy);
	}

	@Override
	public void performAction() throws GameActionException {
	    strategy.performAction(this);
	}

    public SoldierStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(SoldierStrategy strategy) {
        this.strategy = strategy;
    }

	public RouteStrategy getRouteStrategy() {
		return routeStrategy;
	}

	public void setRouteStrategy(RouteStrategy routeStrategy) {
		this.routeStrategy = routeStrategy;
	}

}
