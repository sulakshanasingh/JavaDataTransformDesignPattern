package com.pay.reconcile;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.opencsv.bean.CsvToBeanBuilder;
import com.pay.reconcile.model.CustomerOrder;
import com.pay.reconcile.service.OrderAmountFilterStrategy;
import com.pay.reconcile.service.ValidationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class PaymentReconciliationApplication implements CommandLineRunner {
	@Autowired
	ValidationStrategy validationStrategy;
	@Autowired
	OrderAmountFilterStrategy orderAmountFilterStrategy;

	public static void main(String[] args) {
		SpringApplication.run(PaymentReconciliationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String csvPath ="C:\\Users\\rsula\\project\\reconcile\\src\\main\\resources\\paymentReconcileRecords.csv";
		//STEP 1: parse csv file using opencsv library
		List<CustomerOrder> customerOrderList = new CsvToBeanBuilder<CustomerOrder>(new FileReader(csvPath))
				.withType(CustomerOrder.class).build().parse();
		System.out.println("Total order size of csv dataset is "+customerOrderList.size());
		//STEP 2: Transform dataset - filter invalid record using different filter strategy
		List<CustomerOrder> filterOrderList=customerOrderList.stream()
				.filter(customerOrder -> validationStrategy.apply(customerOrder))
				.filter(customerOrder -> orderAmountFilterStrategy.apply(customerOrder))
				.collect(Collectors.toList());
		System.out.println("Total valid orders after applying filter are "+filterOrderList.size());
		//STEP 3: Build Report
		//failed Transaction list
        List<CustomerOrder> failedTransactions=filterOrderList.stream().map(customerOrder ->
				CustomerOrder.Builder.newInstance()
						.setOrderId(customerOrder.getOrderId())
						.setStatus(customerOrder.getStatus())
						.setAmount(customerOrder.getAmount()).build()
		).filter(customerOrder -> customerOrder.getStatus()
				.equalsIgnoreCase(PaymentStatus.Failed.name()))
				.collect(Collectors.toList());
		System.out.println("Total failed orders are "+failedTransactions.size());
		//pending Transaction list
		List<CustomerOrder> pendingTransactions=filterOrderList.stream().map(customerOrder ->
						CustomerOrder.Builder.newInstance()
								.setOrderId(customerOrder.getOrderId())
								.setStatus(customerOrder.getStatus())
								.setAmount(customerOrder.getAmount()).build()
				).filter(customerOrder -> customerOrder.getStatus()
						.equalsIgnoreCase(PaymentStatus.Pending.name()))
				.collect(Collectors.toList());
		System.out.println("Total pending orders are "+pendingTransactions.size());
	}
}
