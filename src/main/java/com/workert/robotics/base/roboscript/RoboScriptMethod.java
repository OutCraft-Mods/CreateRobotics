package com.workert.robotics.base.roboscript;
public class RoboScriptMethod extends RoboScriptFunction {
	final RoboScriptObject instance;

	RoboScriptMethod(RoboScriptFunction function, RoboScriptObject instance) {
		super(function.address, function.argumentCount);
		this.instance = instance;
	}

	@Override
	public void call(VirtualMachine vm, byte argumentCount) {
		if (this.argumentCount != argumentCount)
			throw new RuntimeError("Expected " + this.argumentCount + " argument(s) but got " + argumentCount + ".");
		RoboScriptObject instance;
		if (this.instance.settable)
			instance = this.instance;
		else
			instance = ((RoboScriptSuper) this.instance).subclassObject;
		vm.pushStack(instance);
		if (instance.clazz.superclass != null)
			vm.pushStack(new RoboScriptSuper(instance.clazz.superclass, instance));
		else vm.pushStack(null);
		argumentCount += 2;
		vm.pushStack(vm.ip);
		vm.pushStack(vm.bp);

		vm.ip = this.address;
		vm.bp = vm.stackSize - argumentCount - 2;
	}
}
