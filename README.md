# `FPL@autoCMS - 10 Quiz 10 Point Easy!`

## `Cài đặt môi trường`
- Tổ hợp phím `Window`+`R`, nhập `cmd` nhấn `ENTER`.
- Trong cửa sổ `Command Prompt` nhập `java -version`.
- Hiện `java version XXX...` nghĩa là đã cài đặt môi trường thành công (bỏ qua bước cài đặt tiếp theo....)
- Nếu xuất hiện lỗi các bạn hãy cài đặt JDK ([Download JDK 1.8 64b](https://drive.google.com/file/d/1tlgwgwbLNqszE6DUCILPIRqrAaCrPvbe/view))
- Thực hiện lại bước 2, nếu không có lỗi là đã thành công!

## `Cách sử dụng`
- Xem clip hướng dẫn chi tiết: [Hướng dẫn sử dụng Tool FPL@UTOCMS v3](https://www.youtube.com/watch?v=kJQZ7rn1YXg)

## `Lịch sử cập nhật`

### v3.3.1
- Cập nhật lại máy chủ!

### v3.3.0
- Cập nhật lại full source (Update lớn nhất từ trước đến nay)
- fix vỡ layout khi có số lượng quiz > 10
- cập nhật lại giao diện hoàn chỉnh hơn
- Không load lại Danh sách quiz nếu đã load lần đầu rồi
- sắp xếp các quiz chuẩn hơn, từ 1 -> final test (sử dụng thuật toán mới)

### v3.2.9:
- Thêm `Main.ADMIN_QUIZ_SAFETY` đặt độ an toàn số lượng quiz
- Update lại toàn bộ source code Client
- Update Server, Request độ tin cậy số lượng quiz mỗi khóa học (Tránh sót quiz không làm vì mạng lag)
- Update Client, Phát hiện không đủ quiz hoặc số lượng quiz chưa an toàn sẽ thông báo!
- Thêm số lượng quiz đủ sẵn 6 môn (Eng 1.1, 1.2, tin học, Dự án mẫu JAVA, pháp luật chính trị)
- Fix lỗi server, [thống kê](https://poly.g88.us/?t=analysis) hiện tháng 0 thay vì tháng 1 

### V3.2.8.2:
- hiển thị thêm số điểm hiện tại và số điểm cần đạt khi chọn quiz muốn auto

### V3.2.8.1:
- update full source code

### V3.2.8:
- thêm điều khiển app(tắt sử dụng các phiên bản cũ, tắt sử dụng khi bảo trì)
- thêm tính năng `debug_APP` (khong gửi request test lên server) (`Main.ADMIN_DEBUG_APP = true`)
- fix lỗi tìm không đủ QUiz môn tiếng anh 1.2 (FA2020)
- cập nhật lại model object
- Hiện tên môn thay vì mã môn Select Course

### V3.2.7:
- sửa lại thống kê sử dụng
- update server ( thêm xem thống kê trên server)

### V3.2.6:
- thêm thống kê sử dụng
- fix lỗi đăng nhập cookie sai mà không thông báo đăng nhập thất bại

### v3.2.3:
- fix lỗi không hiển thị tên quiz

### v3.2.2:
- fix lỗi k chạy đc 1 quiz
- fix lỗi chạy không đúng quiz trên form
- fix lỗi hoàn thành quiz rồi mà vẫn phải đợi 1 phút
- fix lỗi hiển thị
- fix lỗi bị treo khi giải bải chứa đáp án tự luận

### V3.2.1:
- update lại giao diện
- hiển thị thông tin auto ra màn hình

### V3.2.0:
- update `CMSSolution.java`
- bỏ qua câu hỏi chứa đáp án tự luận
- không bỏ qua cả bài chứa đáp án tự luận
- fix lỗi k giải bài

### V3.0.1:
- Update CMSLogin 
- Fix Object CMSAccount