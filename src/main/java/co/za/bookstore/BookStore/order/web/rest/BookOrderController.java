package co.za.bookstore.BookStore.order.web.rest;


import co.za.bookstore.BookStore.order.entity.BookOrder;
import co.za.bookstore.BookStore.order.service.interfaces.BookOrderService;
import co.za.bookstore.BookStore.order.web.dto.BookOrderDto;
import co.za.bookstore.BookStore.order.web.dto.CreateBookOrderForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class BookOrderController {

    private final BookOrderService bookOrderService;

    public ResponseEntity<BookOrderDto> create(@RequestBody CreateBookOrderForm createBookOrderForm) {
        BookOrder createdBookOrder = bookOrderService.create(createBookOrderForm.getBookIsbn(), createBookOrderForm.getOrderQuantity());
        return new ResponseEntity<>(new BookOrderDto(createdBookOrder), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Set<BookOrderDto>> getAll() {
        Set<BookOrder> aLlBookOrders = bookOrderService.getALl();
        Set<BookOrderDto> allBookOrdersDto = new HashSet<>();
        aLlBookOrders.forEach(bookOrder -> allBookOrdersDto.add(new BookOrderDto(bookOrder)));
        return ResponseEntity.ok(allBookOrdersDto);
    }

    @GetMapping("complete/{orderReference}")
    public ResponseEntity<BookOrderDto> complete(@PathVariable("orderReference") String orderReference) {
        BookOrder completedBookOrder = bookOrderService.compete(orderReference);
        return ResponseEntity.ok(new BookOrderDto(completedBookOrder));
    }


    @GetMapping("get/{orderReference}")
    public ResponseEntity<BookOrderDto> getByOrderReference(@PathVariable("orderReference") String orderReference) {
        BookOrder bookOrder = bookOrderService.getByOrderReference(orderReference);
        return ResponseEntity.ok(new BookOrderDto(bookOrder));
    }

}
