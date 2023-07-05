package com.workert.robotics.base.roboscriptbytecode;
import java.util.List;
import java.util.Stack;

import static com.workert.robotics.base.roboscriptbytecode.OpCode.*;

public class VirtualMachine {
	private RoboScript roboScriptInstance;
	private Chunk chunk;
	private Stack<Object> stack = new Stack<>();
	private int instructionPointer = 0;


	VirtualMachine(RoboScript instance) {
		this.roboScriptInstance = instance;
	}


	// this is the function that will be called when the computer or drone or whatever actually needs to run, and it will pass in a string
	protected void interpret(String source) {
		try {
			this.chunk = this.compile(source);
		} catch (CompileError e) {
			// TODO: change this to actually correctly match information
			this.roboScriptInstance.reportCompileError(0, e.toString());
		}
		this.instructionPointer = 0;
		this.run();
	}

	// use this function for assembly testing
	protected void interpret(Chunk chunk) {
		this.chunk = chunk;
		this.instructionPointer = 0;
		this.run();
	}

	// heart of the vm, most of the time spent running the program will live here
	private void run() {
		while (true) {
			byte instruction;
			switch (instruction = this.readByte()) {
				case OP_CONSTANT -> {
					Object constant = this.readConstant();
					this.pushStack(constant);
					System.out.println(constant);
					break;
				}
				case OP_ADD -> this.binaryOperation('+');
				case OP_SUBTRACT -> this.binaryOperation('-');
				case OP_MULTIPLY -> this.binaryOperation('*');
				case OP_DIVIDE -> this.binaryOperation('/');
				case OP_NEGATE -> {
					try {
						this.stack.set(this.stack.size() - 1, -(double) this.stack.peek());
					} catch (ClassCastException e) {
						throw new RuntimeError("Can only negate numbers");
					}
				}
				case OP_RETURN -> {
					System.out.println(this.popStack());
					return;
				}
			}
		}
	}

	private Chunk compile(String source) {
		Scanner scanner = new Scanner(this.roboScriptInstance, source);
		List<Token> tokens = scanner.scanTokens();
		

		return null;
	}


	private byte readByte() {
		return this.chunk.readCode(this.instructionPointer++);
	}

	private Object readConstant() {
		return this.chunk.readConstant(this.readByte());
	}

	private void debugTraceExecution() {
		System.out.print("          ");
		for (Object slot : this.stack) {
			System.out.print("[ " + slot + " ]");
		}
		System.out.println();
		Printer.disassembleInstruction(this.chunk, this.instructionPointer);
	}

	protected void pushStack(Object object) {
		this.stack.push(object);
	}


	protected Object popStack() {
		return this.stack.pop();
	}

	protected Object peekStack() {
		return this.stack.peek();
	}


	private void binaryAdd(Object a, Object b) {
		if (a instanceof String || b instanceof String) {
			this.pushStack(a.toString() + b.toString());
			return;
		}
		try {
			this.pushStack((double) a + (double) b);
		} catch (ClassCastException e) {
			throw new RuntimeError("Addition must be between two numbers or a string.");
		}
	}

	private void binaryOperation(char operand) {
		Object a = this.popStack();
		Object b = this.popStack();
		switch (operand) {
			case '+' -> this.binaryAdd(a, b);
			case '-' -> {

				try {
					this.pushStack((double) a + (double) b);
				} catch (ClassCastException e) {
					throw new RuntimeError("Subtraction must be between two numbers.");
				}
			}
			case '*' -> {
				try {
					this.pushStack((double) a * (double) b);
				} catch (ClassCastException e) {
					throw new RuntimeError("Multiplication must be between two numbers.");
				}
			}
			case '/' -> {
				try {
					this.pushStack((double) a / (double) b);
				} catch (ClassCastException e) {
					throw new RuntimeError("Division must be between two numbers.");
				}
			}
		}
	}
}
