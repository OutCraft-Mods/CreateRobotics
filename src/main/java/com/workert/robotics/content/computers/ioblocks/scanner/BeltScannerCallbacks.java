package com.workert.robotics.content.computers.ioblocks.scanner;

import com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;

public class BeltScannerCallbacks {
	public static BeltProcessingBehaviour.ProcessingResult onItemReceived(TransportedItemStack s, TransportedItemStackHandlerBehaviour i, ScannerBehaviour behaviour) {
		if (behaviour.specifics.getKineticSpeed() == 0) return BeltProcessingBehaviour.ProcessingResult.PASS;
		if (behaviour.running) return BeltProcessingBehaviour.ProcessingResult.PASS;
		behaviour.start();
		return BeltProcessingBehaviour.ProcessingResult.HOLD;
	}

	public static BeltProcessingBehaviour.ProcessingResult whenItemHeld(TransportedItemStack s, TransportedItemStackHandlerBehaviour i, ScannerBehaviour behaviour) {
		if (behaviour.specifics.getKineticSpeed() == 0) return BeltProcessingBehaviour.ProcessingResult.PASS;
		if (!behaviour.running) return BeltProcessingBehaviour.ProcessingResult.PASS;
		if (behaviour.runningTicks != PressingBehaviour.CYCLE / 2) return BeltProcessingBehaviour.ProcessingResult.HOLD;

		behaviour.specifics.scanOnBelt(s);

		return BeltProcessingBehaviour.ProcessingResult.HOLD;
	}
}
