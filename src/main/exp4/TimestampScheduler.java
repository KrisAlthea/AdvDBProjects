package main.exp4;

import java.util.*;


public class TimestampScheduler {
	private List<Transaction> transactions = new ArrayList<>();
	private Map<String, Long> dataItemTimestamps = new HashMap<>();

	public void addTransaction (Transaction transaction) {
		transactions.add(transaction);
	}

	// 模拟执行所有事务
	public void executeTransactions () {
		// 根据时间戳对事务进行排序
		transactions.sort(Comparator.comparingLong(t -> t.timestamp));

		// 逐一执行事务
		for (Transaction transaction : transactions) {
			executeTransaction(transaction);
		}
	}

	private void executeTransaction (Transaction transaction) {
		System.out.println("Executing transaction " + transaction.id + " with timestamp " + transaction.timestamp);
		for (Operation operation : transaction.getOperations()) {
			switch (operation.getType()) {
				case READ:
					System.out.println("Transaction " + transaction.id + " reads " + operation.getDataItem());
					// 此处简化处理，真实场景中可能需要更新读时间戳
					break;
				case WRITE:
					// 更新数据项的时间戳为事务的时间戳
					dataItemTimestamps.put(operation.getDataItem(), transaction.timestamp);
					System.out.println("Transaction " + transaction.id + " writes " + operation.getDataItem() + " with new timestamp " + transaction.timestamp);
					break;
			}
		}
	}

	public static void main (String[] args) {
		TimestampScheduler scheduler = new TimestampScheduler();

		// 示例事务初始化
		Transaction t1 = new Transaction(1, System.currentTimeMillis());
		t1.addOperation(new Operation(Operation.Type.WRITE, "Item1"));
		t1.addOperation(new Operation(Operation.Type.READ, "Item2"));

		Transaction t2 = new Transaction(2, System.currentTimeMillis() + 100);
		t2.addOperation(new Operation(Operation.Type.WRITE, "Item2"));
		t2.addOperation(new Operation(Operation.Type.READ, "Item1"));

		scheduler.addTransaction(t1);
		scheduler.addTransaction(t2);

		// 执行所有事务
		scheduler.executeTransactions();
	}
}

