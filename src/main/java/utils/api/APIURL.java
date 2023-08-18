package utils.api;

import bike.BikeHandlers;
import category.CategoryHandlers;
import com.sun.net.httpserver.HttpServer;
import dock.DockHandlers;
import invoice.InvoiceHandlers;
import transaction.TransactionHandlers;

import java.io.IOException;
import java.net.InetSocketAddress;

public class APIURL {
    public static void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/bike/list", new BikeHandlers.BikeListHandler());
        server.createContext("/bike/ebikes", new BikeHandlers.EBikeListHandler());
        server.createContext("/bike/byDockId", new BikeHandlers.BikeByDockIdHandler());
        server.createContext("/bike/byCategoryId", new BikeHandlers.BikeByCategoryIdHandler());
        server.createContext("/bike/info", new BikeHandlers.BikeInfoHandler());
        server.createContext("/bike/barcodeInfo", new BikeHandlers.BikeInfoByBarcodeHandler());
        server.createContext("/category/list", new CategoryHandlers.CategoryListHandler());
        server.createContext("/dock/list", new DockHandlers.DockListHandler());
        server.createContext("/dock/search", new DockHandlers.DockSearchHandler());
        server.createContext("/dock/info", new DockHandlers.DockInfoHandler());
        server.createContext("/invoice/list", new InvoiceHandlers.InvoiceListHandler());
        server.createContext("/invoice/byId", new InvoiceHandlers.InvoiceByIdHandler());
        server.createContext("/invoice/byCustomerId", new InvoiceHandlers.InvoiceByCustomerIdHandler());
        server.createContext("/invoice/create", new InvoiceHandlers.CreateInvoiceHandler());
        server.createContext("/transaction/list", new TransactionHandlers.TransactionListHandler());
        server.createContext("/transaction/active", new TransactionHandlers.ActiveTransactionHandler());
        server.createContext("/transaction/create", new TransactionHandlers.CreateTransactionHandler());
        server.createContext("/transaction/deposit", new TransactionHandlers.GetDepositHandler());
        server.createContext("/transaction/pause", new TransactionHandlers.PauseTransactionHandler());
        server.createContext("/transaction/unPause", new TransactionHandlers.UnPauseTransactionHandler());
        server.setExecutor(null); // Use the default executor
        server.start();
    }
}
